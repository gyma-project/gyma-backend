package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.ProfileMapper;
import com.gyma.gyma.mappers.TrainingTimeMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.repository.DayRepository;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;


import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingTimeServiceTest {

    @InjectMocks
    private TrainingTimeService trainingTimeService;

    @Mock
    private TrainingTimeRepository trainingTimeRepository;

    @Mock
    private TrainingTimeMapper trainingTimeMapper;

    @Mock
    private DayRepository dayRepository;

    @Mock
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    private Profile trainer;
    private TrainingTime trainingTime;

    @Test
    @DisplayName("Deve retornar uma lista paginada de horários de treino")
    public void returnAListOfTrainingTimes() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<TrainingTime> trainingTimePage = new PageImpl<>(Collections.singletonList(trainingTime), pageRequest, 1);

        when(trainingTimeRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(trainingTimePage);

        Page<TrainingTime> result = trainingTimeService.listarTodos(
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                20,
                null,
                UUID.randomUUID(),
                true,
                UUID.randomUUID(),
                null,
                null
        );
        assertNotNull(result);
        assertEquals(
                1,
                result.getTotalElements()
        );
        assertEquals(
                trainingTime,
                result.getContent().get(0)
        );
        verify(trainingTimeRepository).findAll(any(Specification.class), eq(pageRequest));
    }

    @Test
    @DisplayName("Deve retornar um horário de treino pelo ID")
    public void returnTrainingTimeById(){
        Integer id = 1;
        TrainingTime trainingTime = new TrainingTime();
        trainingTime.setId(id);

        when(trainingTimeRepository.findById(id)).thenReturn(Optional.of(trainingTime));

        TrainingTimeDTO trainingTimeDTO = new TrainingTimeDTO(
                20,
                UUID.randomUUID(),
                UUID.randomUUID(),
                true
        );

        when(trainingTimeMapper.toDTO(trainingTime)).thenReturn(trainingTimeDTO);

        TrainingTimeDTO result = trainingTimeService.buscarPorId(id);

        assertNotNull(result, "O resultado não pode ser nulo.");
        assertEquals(trainingTimeDTO, result, "O DTO retornado não é o esperado.");

        verify(trainingTimeRepository, times(1)).findById(id);
        verify(trainingTimeMapper, times(1)).toDTO(trainingTime);
    }

    @Test
    @DisplayName("Deve retornar um horário de treino editado")
    public void returnTrainingTimeUpdate(){
        Integer id = 1;
        TrainingTime trainingTime = new TrainingTime();
        trainingTime.setId(id);
        trainingTime.setStudentsLimit(15);
        trainingTime.setActive(false);

        Profile trainer = new Profile();
        trainer.setKeycloakId(UUID.randomUUID());

        Profile updater = new Profile();
        updater.setKeycloakId(UUID.randomUUID());

        TrainingTimeDTO trainingTimeDTO = new TrainingTimeDTO(
                20,
                trainer.getKeycloakId(),
                updater.getKeycloakId(),
                true
        );

        when(trainingTimeRepository.findById(1)).thenReturn(Optional.of(trainingTime));
        when(profileRepository.findByKeycloakId(trainer.getKeycloakId())).thenReturn(Optional.of(trainer));
        when(profileRepository.findByKeycloakId(updater.getKeycloakId())).thenReturn(Optional.of(updater));
        doAnswer(invocation -> {
            TrainingTimeDTO dto = invocation.getArgument(0);
            TrainingTime entity = invocation.getArgument(1);
            entity.setStudentsLimit(dto.studentsLimit());
            entity.setActive(dto.active());
            return null;
        }).when(trainingTimeMapper).updateEntityFromDTO(trainingTimeDTO, trainingTime);
        when(trainingTimeRepository.save(trainingTime)).thenReturn(trainingTime);

        TrainingTime result = trainingTimeService.editar(1, trainingTimeDTO);

        assertNotNull(result);
        assertEquals(
                20,
                result.getStudentsLimit(),
                "O limite de estudantes deveria ser atualizado para 20"
        );
        assertTrue(
                result.getActive(),
                "O status deveria ser atualizado para ativo"
        );
        assertEquals(trainer, result.getTrainer());
        assertEquals(updater, result.getUpdateBy());
        verify(trainingTimeRepository).save(trainingTime);
    }

    @Test
    @DisplayName("Deve lançar exceção quando horário de treino não for encontrado")
    public void shouldThrowExceptionWhenTrainingTimeNotFound() {
        Integer id = 1;
        TrainingTimeDTO trainingTimeDTO = new TrainingTimeDTO(
                20,
                UUID.randomUUID(),
                UUID.randomUUID(),
                true
        );

        when(trainingTimeRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> trainingTimeService.editar(id, trainingTimeDTO)
        );

        assertEquals("Horário de treino não encontrado.", exception.getMessage());
        verify(trainingTimeRepository).findById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando treinador não for encontrado")
    public void shouldThrowExceptionWhenTrainerNotFound() {
        Integer id = 1;
        UUID trainerId = UUID.randomUUID();
        TrainingTime trainingTime = new TrainingTime();
        TrainingTimeDTO trainingTimeDTO = new TrainingTimeDTO(
                20,
                trainerId,
                UUID.randomUUID(),
                true
        );

        when(trainingTimeRepository.findById(id)).thenReturn(Optional.of(trainingTime));
        when(profileRepository.findByKeycloakId(trainerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> trainingTimeService.editar(id, trainingTimeDTO)
        );

        assertEquals("Treinador não encontrado.", exception.getMessage());
        verify(trainingTimeRepository).findById(id);
        verify(profileRepository).findByKeycloakId(trainerId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário de atualização não for encontrado")
    public void shouldThrowExceptionWhenUpdateUserNotFound() {
        Integer id = 1;
        UUID trainerId = UUID.randomUUID();
        UUID updaterId = UUID.randomUUID();
        TrainingTime trainingTime = new TrainingTime();
        TrainingTimeDTO trainingTimeDTO = new TrainingTimeDTO(
                20,
                trainerId,
                updaterId,
                true
        );

        when(trainingTimeRepository.findById(id)).thenReturn(Optional.of(trainingTime));
        when(profileRepository.findByKeycloakId(trainerId)).thenReturn(Optional.of(new Profile()));
        when(profileRepository.findByKeycloakId(updaterId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> trainingTimeService.editar(id, trainingTimeDTO)
        );

        assertEquals("Usuário de atualização não encontrado.", exception.getMessage());
        verify(trainingTimeRepository).findById(id);
        verify(profileRepository).findByKeycloakId(trainerId);
        verify(profileRepository).findByKeycloakId(updaterId);
    }


}
