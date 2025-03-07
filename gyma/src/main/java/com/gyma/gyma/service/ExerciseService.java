package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.ExerciseDTO;
import com.gyma.gyma.specificiations.ExerciseSpecifications;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.ExerciseMapper;
import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.model.enums.MuscleGroup;
import com.gyma.gyma.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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

    public Page<Exercise> listar(
            MuscleGroup muscleGroup,
            String name,
            Integer amount,
            Integer repetition,
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

        Specification<Exercise> spec = Specification
                .where(ExerciseSpecifications.byMuscleGroup(muscleGroup))
                .and(ExerciseSpecifications.byName(name))
                .and(ExerciseSpecifications.byAmount(amount))
                .and(ExerciseSpecifications.byRepetition(repetition));

        return exerciseRepository.findAll(spec, page);
    }

    public void deletar(Integer id){
        exerciseRepository.deleteById(id);
    }

}
