package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.mappers.TrainingTimeMapper;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainingTimeService {

    @Autowired
    private TrainingTimeRepository trainingTimeRepository;

    @Autowired
    private TrainingTimeMapper trainingTimeMapper;

    public TrainingTime salvar(TrainingTimeDTO trainingTimeDTO){
        TrainingTime training = trainingTimeMapper.toEntity(trainingTimeDTO);
        return trainingTimeRepository.save(training);
    }

    public TrainingTime editar(Integer id, TrainingTimeDTO trainingTimeDTO){
        Optional<TrainingTime> optionalTrainingTime = trainingTimeRepository.findById(id);

        if(optionalTrainingTime.isPresent()){
            TrainingTime trainingTime = optionalTrainingTime.get();
            trainingTimeMapper.updateEntityFromDTO(trainingTimeDTO, trainingTime);
            return trainingTimeRepository.save(trainingTime);
        }   else {
            throw new RuntimeException("TrainingTime não encontrado para o id: " + id);
        }
    }

}
