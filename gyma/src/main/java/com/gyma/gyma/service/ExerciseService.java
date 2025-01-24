package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.ExerciseDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.ExerciseMapper;
import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseMapper exerciseMapper;

    public ExerciseDTO salvar(ExerciseDTO exerciseDTO){
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDTO.name());
        exercise.setAmount(exerciseDTO.amount());
        exercise.setMuscleGroup(exerciseDTO.muscleGroup());
        exercise.setRepetition(exerciseDTO.repetition());

        exerciseRepository.save(exercise);

        return exerciseMapper.toDTO(exercise);
    }

    public ExerciseDTO editar(Integer id, ExerciseDTO exerciseDTO){
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercício não encontrado com o id: " + id));

        exercise.setName(exerciseDTO.name());
        exercise.setAmount(exerciseDTO.amount());
        exercise.setMuscleGroup(exerciseDTO.muscleGroup());
        exercise.setRepetition(exerciseDTO.repetition());

        exerciseRepository.save(exercise);

        return exerciseMapper.toDTO(exercise);
    }

    public List<Exercise> listar(){
        return exerciseRepository.findAll();
    }

    public void deletar(Integer id){
        exerciseRepository.deleteById(id);
    }

}
