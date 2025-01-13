package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.TrainingTimeMapper;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.repository.DayRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingTimeService {

    @Autowired
    private TrainingTimeRepository trainingTimeRepository;

    @Autowired
    private TrainingTimeMapper trainingTimeMapper;

    @Autowired
    private DayRepository dayRepository;

    public List<TrainingTime> listarTodos() {
        return trainingTimeRepository.findAll();
    }

    public TrainingTimeDTO buscarPorId(Integer id){
        TrainingTime trainingTime = trainingTimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horário de treino não encontrado."));
        return trainingTimeMapper.toDTO(trainingTime);
    }

    public TrainingTime editar(Integer id, TrainingTimeDTO trainingTimeDTO){
        Optional<TrainingTime> optionalTrainingTime = trainingTimeRepository.findById(id);

        if(optionalTrainingTime.isPresent()){
            TrainingTime trainingTime = optionalTrainingTime.get();
            trainingTimeMapper.updateEntityFromDTO(trainingTimeDTO, trainingTime);
            return trainingTimeRepository.save(trainingTime);
        }   else {
            throw new ResourceNotFoundException("Horário de treino não encontrado.");
        }
    }

}