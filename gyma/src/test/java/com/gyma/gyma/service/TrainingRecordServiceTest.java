package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.TrainingRecordMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TrainingRecordRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingRecordServiceTest {

    @InjectMocks
    private TrainingRecordService trainingRecordService;

    @Mock
    private TrainingRecordRepository trainingRecordRepository;

    @Mock
    private TrainingTimeRepository trainingTimeRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private TrainingRecordMapper trainingRecordMapper;

    @Test
    void listarTodos() {
        // Arrange
        TrainingRecord trainingRecord = new TrainingRecord();
        when(trainingRecordRepository.findAll()).thenReturn(List.of(trainingRecord));

        // Act
        var result = trainingRecordService.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(trainingRecordRepository, times(1)).findAll();
    }

    @Test
    void agendar() {
        // Arrange
        Integer trainingTimeId = 1;
        UUID studentId = UUID.randomUUID();
        UUID trainerId = UUID.randomUUID();

        TrainingRecordDTO trainingRecordDTO = new TrainingRecordDTO(trainingTimeId, studentId, trainerId);
        TrainingRecord trainingRecord = new TrainingRecord();
        Profile student = new Profile();
        Profile trainer = new Profile();
        TrainingTime trainingTime = new TrainingTime();

        trainingRecord.setTrainingTime(trainingTime);
        trainingRecord.setStudent(student);
        trainingRecord.setTrainer(trainer);

        when(trainingTimeRepository.findById(trainingTimeId)).thenReturn(Optional.of(trainingTime));
        when(profileRepository.findByKeycloakId(studentId)).thenReturn(Optional.of(student));
        when(profileRepository.findByKeycloakId(trainerId)).thenReturn(Optional.of(trainer));
        when(trainingRecordRepository.save(any(TrainingRecord.class))).thenReturn(trainingRecord);
        when(trainingRecordMapper.toDTO(trainingRecord)).thenReturn(trainingRecordDTO);

        // Act
        var result = trainingRecordService.agendar(trainingRecordDTO);

        // Assert
        assertNotNull(result);
        assertEquals(trainingRecordDTO, result);

        // Verificar se o método save foi chamado com o objeto esperado
        ArgumentCaptor<TrainingRecord> captor = ArgumentCaptor.forClass(TrainingRecord.class);
        verify(trainingRecordRepository, times(1)).save(captor.capture());
        TrainingRecord capturedTrainingRecord = captor.getValue();

        assertEquals(trainingTime, capturedTrainingRecord.getTrainingTime());
        assertEquals(student, capturedTrainingRecord.getStudent());
        assertEquals(trainer, capturedTrainingRecord.getTrainer());
    }

    @Test
    void agendarTrainingTimeNotFound() {
        // Arrange
        Integer trainingTimeId = 1;
        UUID studentId = UUID.randomUUID();
        UUID trainerId = UUID.randomUUID();
        TrainingRecordDTO trainingRecordDTO = new TrainingRecordDTO(trainingTimeId, studentId, trainerId);

        when(trainingTimeRepository.findById(trainingTimeId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trainingRecordService.agendar(trainingRecordDTO);
        });

        assertEquals("Training Time não encontrado por id", exception.getMessage());
    }

    @Test
    void agendarStudentNotFound() {
        // Arrange
        Integer trainingTimeId = 1;
        UUID studentId = UUID.randomUUID();
        UUID trainerId = UUID.randomUUID();
        TrainingRecordDTO trainingRecordDTO = new TrainingRecordDTO(trainingTimeId, studentId, trainerId);

        TrainingTime trainingTime = new TrainingTime();
        when(trainingTimeRepository.findById(trainingTimeId)).thenReturn(Optional.of(trainingTime));
        when(profileRepository.findByKeycloakId(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trainingRecordService.agendar(trainingRecordDTO);
        });

        assertEquals("Student não encontrado por UUID", exception.getMessage());
    }

    @Test
    void agendarTrainerNotFound() {
        // Arrange
        Integer trainingTimeId = 1;
        UUID studentId = UUID.randomUUID();
        UUID trainerId = UUID.randomUUID();
        TrainingRecordDTO trainingRecordDTO = new TrainingRecordDTO(trainingTimeId, studentId, trainerId);

        TrainingTime trainingTime = new TrainingTime();
        Profile student = new Profile();
        when(trainingTimeRepository.findById(trainingTimeId)).thenReturn(Optional.of(trainingTime));
        when(profileRepository.findByKeycloakId(studentId)).thenReturn(Optional.of(student));
        when(profileRepository.findByKeycloakId(trainerId)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trainingRecordService.agendar(trainingRecordDTO);
        });

        assertEquals("Trainer não encontrado por UUID", exception.getMessage());
    }

    @Test
    void deletar() {
        // Arrange
        Integer id = 1;

        // Act
        trainingRecordService.deletar(id);

        // Assert
        verify(trainingRecordRepository, times(1)).deleteById(id);
    }
}
