package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingSheetDTO;
import com.gyma.gyma.mappers.TrainingSheetMapper;
import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.model.TrainingSheet;
import com.gyma.gyma.repository.ExerciseRepository;
import com.gyma.gyma.repository.TrainingSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingSheetService {

    @Autowired
    private TrainingSheetRepository trainingSheetRepository;

    @Autowired
    private TrainingSheetMapper trainingSheetMapper;

    @Autowired
    private ExerciseRepository exerciseRepository;

    public TrainingSheetDTO salvar(TrainingSheetDTO trainingSheetDTO){

        TrainingSheet trainingSheet = new TrainingSheet();
        trainingSheet.setStudent(trainingSheetDTO.student());
        trainingSheet.setDescription(trainingSheetDTO.description());
        trainingSheet.setTrainer(trainingSheetDTO.trainer());
        trainingSheet.setIdUsuario(trainingSheetDTO.idUsuario());

        List<Exercise> exercises = exerciseRepository.findAllById(
                trainingSheetDTO.exerciseIds()
        );

        if (exercises.size() != trainingSheetDTO.exerciseIds().size()) {
            throw new RuntimeException("Alguns IDs de exercícios são inválidos.");
        }

        trainingSheet.setExercises(exercises);


        return trainingSheetMapper.toDTO(trainingSheetRepository.save(trainingSheet));

    }

    public List<TrainingSheet> listar(){
        return trainingSheetRepository.findAll();
    }
}
