package com.gyma.gyma.service;
import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.controller.specificiations.TrainingRecordSpecification;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.exception.StudentLimitExceededException;
import com.gyma.gyma.exception.TrainingTimeNotAvailableException;
import com.gyma.gyma.mappers.TrainingRecordMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.model.TrainingTime;
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
import com.gyma.gyma.model.TrainingRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

        String templateRelatorio =
                "Relatório de Agendamentos de Treinos\n" +
                        "Prezado(a) usuário do GYMA,\n" +
                        "Espero que esteja bem!\n" +
                        "Segue abaixo o relatório detalhado dos agendamentos de treinos realizados. Este relatório visa fornecer uma visão clara sobre as sessões agendadas e a participação dos alunos.\n" +
                        "Resumo dos Agendamentos:\n" +
                        "    Número total de treinos agendados: [Número total]\n" +
                        "    Número de treinos realizados: [Número realizado]\n" +
                        "    Número de treinos não comparecidos: [Número não comparecido]\n" +
                        "    Treinos mais agendados: [Exemplo: \"Musculação\", \"Yoga\", etc.]" +
                        "Caso haja qualquer dúvida ou se precisar de mais informações, estou à disposição para ajudar.\n" +
                        "\n" +
                        "Agradecemos pela confiança e estamos sempre à disposição para continuar oferecendo o melhor serviço para você e seus alunos.\n" +
                        "\n" +
                        "Atenciosamente, GYMA.";

        //Pegar emails
        List<Profile> adminProfiles = profileRepository.findByRoles_Name("ADMIN");
        List<String> emailList = adminProfiles.stream()
                .map(Profile::getEmail)
                .toList();

        System.out.println(emailList);

        String mensagemRelatorio = "dinizrobert2002@gmail.com;" + templateRelatorio;



        rabbitTemplate.convertAndSend("notify", mensagemRelatorio);

        return mensagemRelatorio;
    }


}
