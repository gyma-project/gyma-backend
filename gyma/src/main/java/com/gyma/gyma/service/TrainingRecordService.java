package com.gyma.gyma.service;
import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.TrainingRecordMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TrainingRecordRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gyma.gyma.model.TrainingRecord;

import java.util.List;
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

    public List<TrainingRecord> listarTodos(){
        return trainingRecordRepository.findAll();
    }

    public TrainingRecordDTO agendar(TrainingRecordDTO dto){

        var trainingTime = trainingTimeRepository.findById(dto.trainingTimeId())
                .orElseThrow(() -> new ResourceNotFoundException("Training Time não encontrado por id"));
        var student = profileRepository.findByKeycloakId(dto.student())
                .orElseThrow(() -> new ResourceNotFoundException("Student não encontrado por UUID"));
        var trainer = profileRepository.findByKeycloakId(dto.trainer())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer não encontrado por UUID"));

        TrainingRecord trainingRecord = new TrainingRecord();
        trainingRecord.setTrainingTime(trainingTime);
        trainingRecord.setStudent(student);
        trainingRecord.setTrainer(trainer );

        trainingRecord = trainingRecordRepository.save(trainingRecord);

        return trainingRecordMapper.toDTO(trainingRecord);
    }

    public void deletar(Integer id){
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

        String mensagemRelatorio = "willianm1928@gmail.com;" + templateRelatorio;



        rabbitTemplate.convertAndSend("notify", mensagemRelatorio);

        return mensagemRelatorio;
    }
}
