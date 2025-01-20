package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.TrainingRecordMapper;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TrainingRecordRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gyma.gyma.model.TrainingRecord;

import java.util.List;

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

}
