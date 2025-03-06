package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.ExerciseDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.ExerciseMapper;
import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.model.enums.MuscleGroup;
import com.gyma.gyma.repository.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import org.mockito.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

class ExerciseServiceRegressionTest {

    @InjectMocks
    private ExerciseService exerciseService;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseMapper exerciseMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Teste de regressao para o metodo salvar
    @Test
    void testSalvar_Regression() {
        // Mock do DTO de entrada
        ExerciseDTO dto = mock(ExerciseDTO.class);
        when(dto.name()).thenReturn("Push-up");
        when(dto.amount()).thenReturn(20);
        when(dto.muscleGroup()).thenReturn(MuscleGroup.CHEST);
        when(dto.repetition()).thenReturn(10);

        // Mock da entidade Exercise
        Exercise exercise = new Exercise();
        exercise.setName("Push-up");
        exercise.setAmount(20);
        exercise.setMuscleGroup(MuscleGroup.CHEST);
        exercise.setRepetition(10);

        // Mock do mapeamento
        when(exerciseMapper.toDTO(any(Exercise.class))).thenReturn(dto);
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        // Execução do metodo
        ExerciseDTO result = exerciseService.salvar(dto);

        // Verificacoes
        assertNotNull(result);
        assertEquals("Push-up", result.name());
        assertEquals(20, result.amount());
        assertEquals(MuscleGroup.CHEST, result.muscleGroup());
        assertEquals(10, result.repetition());

        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    // Teste de regressao para o metodo editar
    @Test
    void testEditar_Regression() {
        Integer id = 1;
        ExerciseDTO dto = mock(ExerciseDTO.class);
        when(dto.name()).thenReturn("Squat");
        when(dto.amount()).thenReturn(15);
        when(dto.muscleGroup()).thenReturn(MuscleGroup.LEGS);
        when(dto.repetition()).thenReturn(12);

        // Mock da entidade Exercise
        Exercise exercise = new Exercise();
        exercise.setId(id);
        exercise.setName("Squat");
        exercise.setAmount(15);
        exercise.setMuscleGroup(MuscleGroup.LEGS);
        exercise.setRepetition(12);

        // Mock do comportamento do repositorio
        when(exerciseRepository.findById(id)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);
        when(exerciseMapper.toDTO(any(Exercise.class))).thenReturn(dto);

        // Execução do metodo
        ExerciseDTO result = exerciseService.editar(id, dto);

        // Verificacoes
        assertNotNull(result);
        assertEquals("Squat", result.name());
        assertEquals(15, result.amount());
        assertEquals(MuscleGroup.LEGS, result.muscleGroup());
        assertEquals(12, result.repetition());

        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    // Teste de regressao para o metodo listar
    @Test
    void testListar_Regression() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Exercise> exercisesPage = mock(Page.class);

        // Especificando explicitamente a assinatura do metodo correto
        when(exerciseRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(exercisesPage);

        // Execução do metodo
        Page<Exercise> result = exerciseService.listar(MuscleGroup.CHEST, "Push-up", 20, 10, 0, 10);

        // Verificacoes
        assertNotNull(result);
        verify(exerciseRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    // Teste de regressao para o metodo deletar
    @Test
    void testDeletar_Regression() {
        Integer id = 1;

        // Execução do metodo
        exerciseService.deletar(id);

        // Verificacoes
        verify(exerciseRepository, times(1)).deleteById(id);
    }
}
