package com.gyma.gyma.service;
import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.model.enums.CategoryTransaction;
import com.gyma.gyma.repository.TransactionRepository;
import com.gyma.gyma.specificiations.TrainingRecordSpecification;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.exception.StudentLimitExceededException;
import com.gyma.gyma.exception.TrainingTimeNotAvailableException;
import com.gyma.gyma.mappers.TrainingRecordMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TrainingRecordRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TrainingRecordService {

    @Autowired
    private TrainingRecordRepository trainingRecordRepository;

    @Autowired
    private TrainingTimeRepository trainingTimeRepository;

    @Autowired
    private TrainingRecordMapper trainingRecordMapper;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TransactionRepository transactionRepository;


    public Page<TrainingRecord> listarTodos(
            Integer trainingTimeId,
            UUID studentId,
            UUID trainerId,
            LocalDate createdAt,
            LocalDate updatedAt,
            LocalDate startDate,
            LocalDate endDate,
            Integer pageNumber,
            Integer size
    ) {
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (size == null) {
            size = 10;
        }

        Pageable page = PageRequest.of(pageNumber, size);

        Specification<TrainingRecord> spec = Specification.where(
                TrainingRecordSpecification.byTrainingTime(trainingTimeId)
                .and(TrainingRecordSpecification.byStudent(studentId))
                .and(TrainingRecordSpecification.byTrainer(trainerId))
                .and(TrainingRecordSpecification.byCreatedAt(createdAt))
                .and(TrainingRecordSpecification.byUpdatedAt(updatedAt))
                .and(TrainingRecordSpecification.byCreatedAtBetween(startDate, endDate))
                .and(TrainingRecordSpecification.byUpdatedAtBetween(startDate, endDate)
        ));

        return trainingRecordRepository.findAll(spec, page);
    }

    public TrainingRecordDTO agendar(TrainingRecordDTO dto){

        var trainingTime = trainingTimeRepository.findById(dto.trainingTimeId())
                .orElseThrow(() -> new ResourceNotFoundException("Training Time não encontrado por id"));
        var student = profileRepository.findByKeycloakId(dto.student())
                .orElseThrow(() -> new ResourceNotFoundException("Student não encontrado por UUID"));
        var trainer = profileRepository.findByKeycloakId(dto.trainer())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer não encontrado por UUID"));

        if(!trainingTime.getActive()){
            throw new TrainingTimeNotAvailableException("Esse horário não está mais disponível.");
        }
        long appointments = trainingRecordRepository.countByTrainingTimeAndCreatedAt(
                trainingTime, LocalDate.now()
        );
        if(appointments >= trainingTime.getStudentsLimit()){
            throw new StudentLimitExceededException("Limite de alunos para este horário atingido.");
        }

        TrainingRecord trainingRecord = new TrainingRecord();
        trainingRecord.setTrainingTime(trainingTime);
        trainingRecord.setStudent(student);
        trainingRecord.setTrainer(trainer );

        trainingRecord = trainingRecordRepository.save(trainingRecord);

        return trainingRecordMapper.toDTO(trainingRecord);
    }

    public void deletar(Integer id){
        TrainingRecord trainingRecord = trainingRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de treino não encontrado."));
        trainingRecordRepository.deleteById(id);
    }

    public String criarRelatorio() {
        String reportHtml = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Relatório Semanal - GYMA</title>
                </head>
                <body style="margin: 0; padding: 0; background-color: #f3f4f6; font-family: Arial, sans-serif;">
                <table role="presentation" width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#f3f4f6">
                    <tr>
                        <td align="center">
                            <table role="presentation" width="600" cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff" style="margin-top: 20px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
                
                                <!-- Cabeçalho Vermelho -->
                                <tr>
                                    <td align="center" bgcolor="#EF4444" style="border-top-left-radius: 8px; border-top-right-radius: 8px; padding: 10px;">
                                        <h1 style="color: white; font-size: 24px; font-weight: bold; margin: 0;">Relatório Semanal - GYMA</h1>
                                    </td>
                                </tr>
                
                                <!-- Corpo do e-mail -->
                                <tr>
                                    <td style="padding: 20px;">
                                        <p style="text-align: left; color: #4b5563; font-size: 16px;">Prezado(a) usuário do <strong>GYMA</strong>,</p>
                                        <p style="text-align: left; color: #4b5563; font-size: 16px;">Espero que esteja bem!</p>
                                        <p style="text-align: left; color: #4b5563; font-size: 16px;">
                                            Segue abaixo o relatório detalhado dos agendamentos de treinos realizados. Este relatório visa fornecer uma visão clara sobre as sessões agendadas e a participação dos alunos.
                                        </p>
                                        <h3 style="color: #1f2937; font-size: 18px; font-weight: bold; text-align: left;">Resumo dos Agendamentos:</h3>
                                        <ul style="text-align: left; color: #4b5563; font-size: 16px; list-style: none; padding: 0;">
                                            <li>📌 <strong>Número total de treinos agendados:</strong> ${totalTreinos}</li>
                                        </ul>
                                        
                                        <h3 style="color: #1f2937; font-size: 18px; font-weight: bold; text-align: left;">Resumo das Transações:</h3>
                                        <ul style="text-align: left; color: #4b5563; font-size: 16px; list-style: none; padding: 0;">
                                            <li>📌 <strong>Total de transações realizadas:</strong> ${totalTransacoes}</li>
                                            <li>📌 <strong>Valor total transacionado:</strong> ${totalValorTransacionado}</li>
                                            <li>📌 <strong>Categoria mais frequente de transação:</strong> ${categoriaMaisFrequente}</li>
                                        </ul>

                                        <p style="text-align: left; color: #4b5563; font-size: 16px;">
                                            <strong>Atenciosamente,</strong><br>Equipe GYMA
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                </body>
                </html>
        """;

        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        long totalTrainingThisWeek = trainingRecordRepository.countByCreatedAtBetween(startOfWeek, endOfWeek);

        long totalTransactionsThisWeek = transactionRepository.countByCreatedAtBetween(startOfWeek, endOfWeek);
        BigDecimal totalAmountTransacted = transactionRepository.sumPriceByCreatedAtBetween(startOfWeek, endOfWeek);
        String mostFrequentCategory = transactionRepository.findMostFrequentCategory(startOfWeek, endOfWeek);

        String mostFrequentCategoryDescription = CategoryTransaction.valueOf(mostFrequentCategory).getDescription();

        String filledReport = reportHtml
                .replace("${totalTreinos}", String.valueOf(totalTrainingThisWeek))
                .replace("${totalTransacoes}", String.valueOf(totalTransactionsThisWeek))
                .replace("${totalValorTransacionado}", totalAmountTransacted.toString())
                .replace("${categoriaMaisFrequente}", mostFrequentCategoryDescription);

        List<Profile> adminProfiles = profileRepository.findByRoles_Name("ADMIN");
        List<String> emailList = adminProfiles.stream()
                .map(Profile::getEmail)
                .toList();

        String mensagemRelatorio = String.join("|", emailList) + "|" + filledReport;
        rabbitTemplate.convertAndSend("notify", mensagemRelatorio);

        return mensagemRelatorio;
    }

}
