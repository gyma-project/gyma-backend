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
        TrainingRecord trainingRecord = new TrainingRecord();
        when(trainingRecordRepository.findAll()).thenReturn(List.of(trainingRecord));

        var result = trainingRecordService.listarTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(trainingRecordRepository, times(1)).findAll();
    }

    @Test
    void agendar() {

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

        var result = trainingRecordService.agendar(trainingRecordDTO);

        assertNotNull(result);
        assertEquals(trainingRecordDTO, result);

        ArgumentCaptor<TrainingRecord> captor = ArgumentCaptor.forClass(TrainingRecord.class);
        verify(trainingRecordRepository, times(1)).save(captor.capture());
        TrainingRecord capturedTrainingRecord = captor.getValue();

        assertEquals(trainingTime, capturedTrainingRecord.getTrainingTime());
        assertEquals(student, capturedTrainingRecord.getStudent());
        assertEquals(trainer, capturedTrainingRecord.getTrainer());
    }

    @Test
    void agendarTrainingTimeNotFound() {
        Integer trainingTimeId = 1;
        UUID studentId = UUID.randomUUID();
        UUID trainerId = UUID.randomUUID();
        TrainingRecordDTO trainingRecordDTO = new TrainingRecordDTO(trainingTimeId, studentId, trainerId);

        when(trainingTimeRepository.findById(trainingTimeId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trainingRecordService.agendar(trainingRecordDTO);
        });

        assertEquals("Training Time não encontrado por id", exception.getMessage());
    }

    @Test
    void agendarStudentNotFound() {
        Integer trainingTimeId = 1;
        UUID studentId = UUID.randomUUID();
        UUID trainerId = UUID.randomUUID();
        TrainingRecordDTO trainingRecordDTO = new TrainingRecordDTO(trainingTimeId, studentId, trainerId);

        TrainingTime trainingTime = new TrainingTime();
        when(trainingTimeRepository.findById(trainingTimeId)).thenReturn(Optional.of(trainingTime));
        when(profileRepository.findByKeycloakId(studentId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trainingRecordService.agendar(trainingRecordDTO);
        });

        assertEquals("Student não encontrado por UUID", exception.getMessage());
    }

    @Test
    void agendarTrainerNotFound() {
        Integer trainingTimeId = 1;
        UUID studentId = UUID.randomUUID();
        UUID trainerId = UUID.randomUUID();
        TrainingRecordDTO trainingRecordDTO = new TrainingRecordDTO(trainingTimeId, studentId, trainerId);

        TrainingTime trainingTime = new TrainingTime();
        Profile student = new Profile();
        when(trainingTimeRepository.findById(trainingTimeId)).thenReturn(Optional.of(trainingTime));
        when(profileRepository.findByKeycloakId(studentId)).thenReturn(Optional.of(student));
        when(profileRepository.findByKeycloakId(trainerId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trainingRecordService.agendar(trainingRecordDTO);
        });

        assertEquals("Trainer não encontrado por UUID", exception.getMessage());
    }

    @Test
    void deletar() {
        Integer id = 1;

        TrainingRecord trainingRecord = new TrainingRecord();
        when(trainingRecordRepository.findById(id)).thenReturn(Optional.of(trainingRecord));

        trainingRecordService.deletar(id);

        verify(trainingRecordRepository, times(1)).deleteById(id);
    }

    @Test
    void deletarQuandoIdInvalido() {
        Integer idInvalido = -5;

        when(trainingRecordRepository.findById(idInvalido)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trainingRecordService.deletar(idInvalido);
        });

        assertEquals("Registro de treino não encontrado.", exception.getMessage());

        verify(trainingRecordRepository, never()).deleteById(idInvalido);
    }


}
