package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.model.Trainer;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.repository.TrainerRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class TrainingTimeService {

    @Autowired
    private TrainingTimeRepository trainingTimeRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    public TrainingTime salvar(TrainingTime training){
        return trainingTimeRepository.save(training);
    }

    public TrainingTime editar(Integer id, TrainingTimeDTO trainingTimeDTO){
        Optional<TrainingTime> optionalTrainingTime = trainingTimeRepository.findById(id);

        if(optionalTrainingTime.isPresent()){
            TrainingTime trainingTime = optionalTrainingTime.get();
            trainingTime.setDayOfTheWeek(trainingTimeDTO.dayOfTheWeek());
            trainingTime.setStartTime(trainingTimeDTO.startTime());
            trainingTime.setEndTime(trainingTimeDTO.endTime());
            trainingTime.setStudentsLimit(trainingTimeDTO.studentsLimit());
            trainingTime.setActive(trainingTimeDTO.active());


            return trainingTimeRepository.save(trainingTime);

        }   else {
            throw new RuntimeException("TrainingTime não encontrado para o id: " + id);
        }
    }

}
