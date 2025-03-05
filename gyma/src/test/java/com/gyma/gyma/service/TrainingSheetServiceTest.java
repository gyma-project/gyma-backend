package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingSheetDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingSheet;
import com.gyma.gyma.repository.ExerciseRepository;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TrainingSheetRepository;
import com.gyma.gyma.mappers.TrainingSheetMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class TrainingSheetServiceTest {

    @Mock
    private TrainingSheetRepository trainingSheetRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private TrainingSheetMapper trainingSheetMapper;

    @InjectMocks
    private TrainingSheetService trainingSheetService;

    private TrainingSheet trainingSheet;
    private TrainingSheetDTO trainingSheetDTO;
    private Profile student;
    private Profile trainer;
    private Profile updater;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        student = new Profile();
        student.setKeycloakId(UUID.randomUUID());

        trainer = new Profile();
        trainer.setKeycloakId(UUID.randomUUID());

        updater = new Profile();
        updater.setKeycloakId(UUID.randomUUID());

        trainingSheetDTO = new TrainingSheetDTO(
                "Treino Padrão",
                student.getKeycloakId(),
                trainer.getKeycloakId(),
                "Treino de força",
                Arrays.asList(1, 2, 3),
                updater.getKeycloakId()
        );

        trainingSheet = new TrainingSheet();
        trainingSheet.setId(1);
        trainingSheet.setStudent(student);
        trainingSheet.setTrainer(trainer);
        trainingSheet.setUpdateBy(updater);
        trainingSheet.setDescription("Treino de força");
    }

//    @Test
//    void shouldListAllTrainingSheets() {
//        TrainingSheet trainingSheet1 = new TrainingSheet();
//        trainingSheet1.setId(1);
//        trainingSheet1.setDescription("Treino de força");
//
//        TrainingSheet trainingSheet2 = new TrainingSheet();
//        trainingSheet2.setId(2);
//        trainingSheet2.setDescription("Treino de resistência");
//
//        List<TrainingSheet> trainingSheets = Arrays.asList(trainingSheet1, trainingSheet2);
//
//        when(trainingSheetRepository.findAll()).thenReturn(trainingSheets);
//
//        Page<TrainingSheet> result = trainingSheetService.listar(
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals(trainingSheet1.getDescription(), result.get(0).getDescription());
//        assertEquals(trainingSheet2.getDescription(), result.get(1).getDescription());
//        verify(trainingSheetRepository).findAll();
//    }

    @Test
    void shouldSaveTrainingSheet() {
        when(profileRepository.findByKeycloakId(student.getKeycloakId())).thenReturn(Optional.of(student));
        when(profileRepository.findByKeycloakId(trainer.getKeycloakId())).thenReturn(Optional.of(trainer));
        when(profileRepository.findByKeycloakId(updater.getKeycloakId())).thenReturn(Optional.of(updater));
        when(exerciseRepository.findAllById(trainingSheetDTO.exerciseIds())).thenReturn(Arrays.asList(new Exercise(), new Exercise(), new Exercise()));
        when(trainingSheetRepository.save(any(TrainingSheet.class))).thenReturn(trainingSheet);
        when(trainingSheetMapper.toDTO(any(TrainingSheet.class))).thenReturn(trainingSheetDTO);

        TrainingSheetDTO result = trainingSheetService.salvar(trainingSheetDTO);

        assertNotNull(result);
        assertEquals(trainingSheetDTO.description(), result.description());
        assertEquals(trainingSheetDTO.student(), result.student());
        assertEquals(trainingSheetDTO.trainer(), result.trainer());
        verify(trainingSheetRepository).save(any(TrainingSheet.class));
    }

    @Test
    void shouldThrowExceptionWhenProfileNotFound() {
        TrainingSheetDTO trainingSheetDTO = new TrainingSheetDTO(
                "Treino Padrão",
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Treino de força",
                Arrays.asList(1, 2, 3),
                UUID.randomUUID()
        );

        when(profileRepository.findByKeycloakId(trainingSheetDTO.student())).thenReturn(Optional.empty());
        when(profileRepository.findByKeycloakId(trainingSheetDTO.trainer())).thenReturn(Optional.of(new Profile()));
        when(profileRepository.findByKeycloakId(trainingSheetDTO.updateBy())).thenReturn(Optional.of(new Profile()));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trainingSheetService.salvar(trainingSheetDTO);
        });

        assertEquals("Algum perfil não foi encontrado.", exception.getMessage());
        verify(profileRepository).findByKeycloakId(trainingSheetDTO.student());
        verify(profileRepository).findByKeycloakId(trainingSheetDTO.trainer());
        verify(profileRepository).findByKeycloakId(trainingSheetDTO.updateBy());
    }

    @Test
    void shouldThrowExceptionWhenEditProfileNotFound() {
        Integer id = 1;

        TrainingSheetDTO trainingSheetDTO = new TrainingSheetDTO(
                "Treino Padrão",
                UUID.randomUUID(), // Estudante
                UUID.randomUUID(), // Treinador
                "Treino de força",
                Arrays.asList(1, 2, 3),
                UUID.randomUUID()  // Atualizador
        );

        when(trainingSheetRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            trainingSheetService.editar(id, trainingSheetDTO);
        });

        assertEquals("Folha de treino não encontrada.", exception.getMessage());

        verify(trainingSheetRepository).findById(id);

        when(trainingSheetRepository.findById(id)).thenReturn(Optional.of(new TrainingSheet()));

        when(profileRepository.findByKeycloakId(trainingSheetDTO.student())).thenReturn(Optional.empty());
        when(profileRepository.findByKeycloakId(trainingSheetDTO.trainer())).thenReturn(Optional.of(new Profile()));
        when(profileRepository.findByKeycloakId(trainingSheetDTO.updateBy())).thenReturn(Optional.of(new Profile()));

        exception = assertThrows(ResourceNotFoundException.class, () -> {
            trainingSheetService.editar(id, trainingSheetDTO);
        });

        assertEquals("Algum perfil não foi encontrado.", exception.getMessage());

        verify(profileRepository).findByKeycloakId(trainingSheetDTO.student());
        verify(profileRepository).findByKeycloakId(trainingSheetDTO.trainer());
        verify(profileRepository).findByKeycloakId(trainingSheetDTO.updateBy());
    }

    @Test
    void shouldThrowExceptionWhenExerciseIdsAreInvalidOnEdit() {
        Integer id = 1;

        TrainingSheetDTO trainingSheetDTO = new TrainingSheetDTO(
                "Treino Padrão",
                UUID.randomUUID(), // Estudante
                UUID.randomUUID(), // Treinador
                "Treino de força",
                Arrays.asList(1, 2, 3),  // IDs de exercícios
                UUID.randomUUID()  // Atualizador
        );

        when(trainingSheetRepository.findById(id)).thenReturn(Optional.of(new TrainingSheet()));

        when(profileRepository.findByKeycloakId(trainingSheetDTO.student())).thenReturn(Optional.of(new Profile()));
        when(profileRepository.findByKeycloakId(trainingSheetDTO.trainer())).thenReturn(Optional.of(new Profile()));
        when(profileRepository.findByKeycloakId(trainingSheetDTO.updateBy())).thenReturn(Optional.of(new Profile()));

        when(exerciseRepository.findAllById(trainingSheetDTO.exerciseIds())).thenReturn(Arrays.asList(new Exercise(), new Exercise())); // Retorna apenas 2 exercícios

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            trainingSheetService.editar(id, trainingSheetDTO);
        });

        assertEquals("Alguns IDs de exercícios são inválidos.", exception.getMessage());

        verify(trainingSheetRepository).findById(id);
        verify(profileRepository).findByKeycloakId(trainingSheetDTO.student());
        verify(profileRepository).findByKeycloakId(trainingSheetDTO.trainer());
        verify(profileRepository).findByKeycloakId(trainingSheetDTO.updateBy());
        verify(exerciseRepository).findAllById(trainingSheetDTO.exerciseIds());
    }


    @Test
    void shouldThrowExceptionWhenStudentNotFoundOnSave() {
        when(profileRepository.findByKeycloakId(student.getKeycloakId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> trainingSheetService.salvar(trainingSheetDTO));

        assertEquals("Algum perfil não foi encontrado.", exception.getMessage());
    }

    @Test
    void shouldEditTrainingSheet() {
        when(trainingSheetRepository.findById(1)).thenReturn(Optional.of(trainingSheet));
        when(profileRepository.findByKeycloakId(student.getKeycloakId())).thenReturn(Optional.of(student));
        when(profileRepository.findByKeycloakId(trainer.getKeycloakId())).thenReturn(Optional.of(trainer));
        when(profileRepository.findByKeycloakId(updater.getKeycloakId())).thenReturn(Optional.of(updater));
        when(exerciseRepository.findAllById(trainingSheetDTO.exerciseIds())).thenReturn(Arrays.asList(new Exercise(), new Exercise(), new Exercise()));
        when(trainingSheetRepository.save(any(TrainingSheet.class))).thenReturn(trainingSheet);
        when(trainingSheetMapper.toDTO(any(TrainingSheet.class))).thenReturn(trainingSheetDTO);

        TrainingSheetDTO result = trainingSheetService.editar(1, trainingSheetDTO);

        assertNotNull(result);
        assertEquals(trainingSheetDTO.description(), result.description());
        assertEquals(trainingSheetDTO.student(), result.student());
        assertEquals(trainingSheetDTO.trainer(), result.trainer());
        verify(trainingSheetRepository).save(any(TrainingSheet.class));
    }

    @Test
    void shouldThrowExceptionWhenTrainingSheetNotFoundOnEdit() {
        when(trainingSheetRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> trainingSheetService.editar(1, trainingSheetDTO));

        assertEquals("Folha de treino não encontrada.", exception.getMessage());
    }

    @Test
    void shouldDeleteTrainingSheet() {
        doNothing().when(trainingSheetRepository).deleteById(1);

        trainingSheetService.deletar(1);

        verify(trainingSheetRepository).deleteById(1);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTrainingSheet() {
        doThrow(new ResourceNotFoundException("Folha de treino não encontrada.")).when(trainingSheetRepository).deleteById(1);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> trainingSheetService.deletar(1));

        assertEquals("Folha de treino não encontrada.", exception.getMessage());
    }
}
