package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.ExerciseDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.ExerciseMapper;
import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.model.enums.MuscleGroup;
import com.gyma.gyma.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseMapper exerciseMapper;

    @InjectMocks
    private ExerciseService exerciseService;

    private ExerciseDTO exerciseDTO;
    private Exercise exercise;

    @Test
    void testSaveExercise() {
        exerciseDTO = new ExerciseDTO(
                "Supino Reto",
                MuscleGroup.CHEST,
                10,
                3
        );
        exercise = new Exercise();
        exercise.setId(1);
        exercise.setName("Supino Reto");
        exercise.setAmount(10);
        exercise.setMuscleGroup(MuscleGroup.CHEST);
        exercise.setRepetition(3);

        when(exerciseMapper.toDTO(any(Exercise.class))).thenReturn(exerciseDTO);
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        ExerciseDTO result = exerciseService.salvar(exerciseDTO);

        assertNotNull(result);
        assertEquals("Supino Reto", result.name());
        assertEquals(10, result.amount());
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    @Test
    void testEditExercise() {

        exerciseDTO = new ExerciseDTO(
                "Supino Reto",
                MuscleGroup.CHEST,
                10,
                3
        );
        exercise = new Exercise();
        exercise.setId(1);
        exercise.setName("Supino Reto");
        exercise.setAmount(10);
        exercise.setMuscleGroup(MuscleGroup.CHEST);
        exercise.setRepetition(3);

        when(exerciseRepository.findById(1)).thenReturn(Optional.of(exercise));

        when(exerciseMapper.toDTO(any(Exercise.class))).thenReturn(exerciseDTO);

        ExerciseDTO result = exerciseService.editar(1, exerciseDTO);

        assertNotNull(result);
        assertEquals("Supino Reto", result.name());
        assertEquals(10, result.amount());
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    @Test
    void testEditExerciseNotFound() {
        when(exerciseRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> exerciseService.editar(1, exerciseDTO));
    }

    @Test
    void testDeleteExercises() {
        exerciseService.deletar(1);
        verify(exerciseRepository, times(1)).deleteById(1);
    }

    @Test
    void testReturnAListOfExercises() {
        Exercise exercise = new Exercise();
        exercise.setId(1);
        exercise.setName("Supino Reto");
        exercise.setAmount(10);
        exercise.setMuscleGroup(MuscleGroup.CHEST);
        exercise.setRepetition(3);

        List<Exercise> exercises = List.of(exercise);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Exercise> exercisePage = new PageImpl<>(exercises, pageable, exercises.size());

        when(exerciseRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(exercisePage);

        Page<Exercise> result = exerciseService.listar(
                MuscleGroup.CHEST,
                "Supino",
                10,
                3,
                0,
                10
        );

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(exercise.getName(), result.getContent().get(0).getName());
    }

}
