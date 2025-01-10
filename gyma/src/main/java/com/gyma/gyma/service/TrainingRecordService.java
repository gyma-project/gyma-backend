package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.mappers.TrainingRecordMapper;
import com.gyma.gyma.model.TrainingRecord;
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

    public List<TrainingRecord> listarTodos(){
        return trainingRecordRepository.findAll();
    }

    public TrainingRecordDTO criarRegistro(TrainingRecordDTO dto){
        var trainingTime = trainingTimeRepository.findById(dto.trainingTimeId())
                .orElseThrow(() -> new RuntimeException("Training Time não encontrado por id"));

        TrainingRecord trainingRecord = new TrainingRecord();
        trainingRecord.setTrainingTime(trainingTime);
        trainingRecord.setStudent(dto.student());
        trainingRecord.setTrainer(dto.trainer());

        trainingRecord = trainingRecordRepository.save(trainingRecord);

        return trainingRecordMapper.toDTO(trainingRecord);
    }

    public void deletar(Integer id){
        trainingRecordRepository.deleteById(id);
    }

}
