package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.mappers.TrainingTimeMapper;
import com.gyma.gyma.model.Day;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.model.enums.DayOfTheWeek;
import com.gyma.gyma.repository.DayRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public TrainingTime buscarPorId(Integer id){
        Optional<TrainingTime> optionalTrainingTime = trainingTimeRepository.findById(id);
        if(optionalTrainingTime.isPresent()){
            return optionalTrainingTime.get();
        } else {
            throw new RuntimeException("TrainingTime não encontrado para o id: " + id);
        }
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