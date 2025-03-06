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

    @Test
    void shouldSaveTrainingSheetAndVerifyFields() {
        // Mockando dependências
        when(profileRepository.findByKeycloakId(student.getKeycloakId())).thenReturn(Optional.of(student));
        when(profileRepository.findByKeycloakId(trainer.getKeycloakId())).thenReturn(Optional.of(trainer));
        when(profileRepository.findByKeycloakId(updater.getKeycloakId())).thenReturn(Optional.of(updater));
        when(exerciseRepository.findAllById(trainingSheetDTO.exerciseIds()))
                .thenReturn(Arrays.asList(new Exercise(), new Exercise(), new Exercise()));

        when(trainingSheetRepository.save(any(TrainingSheet.class))).thenAnswer(invocation -> {
            TrainingSheet savedSheet = invocation.getArgument(0);
            savedSheet.setId(1);
            return savedSheet;
        });

        when(trainingSheetMapper.toDTO(any(TrainingSheet.class))).thenReturn(trainingSheetDTO);

        // Execução
        TrainingSheetDTO result = trainingSheetService.salvar(trainingSheetDTO);

        // Verificações
        assertNotNull(result);
        assertEquals(trainingSheetDTO.description(), result.description());
        assertEquals(trainingSheetDTO.student(), result.student());
        assertEquals(trainingSheetDTO.trainer(), result.trainer());

        // Verificando que os métodos setter foram chamados corretamente
        ArgumentCaptor<TrainingSheet> captor = ArgumentCaptor.forClass(TrainingSheet.class);
        verify(trainingSheetRepository).save(captor.capture());
        TrainingSheet savedSheet = captor.getValue();

        assertEquals("Treino de força", savedSheet.getDescription());
        assertEquals(student, savedSheet.getStudent());
        assertEquals(trainer, savedSheet.getTrainer());
        assertEquals(updater, savedSheet.getUpdateBy());
        assertEquals(3, savedSheet.getExercises().size());

        verify(trainingSheetRepository).save(any(TrainingSheet.class));
    }

    @Test
    void shouldFindTrainingSheetById() {
        when(trainingSheetRepository.findById(1)).thenReturn(Optional.of(trainingSheet));

        TrainingSheet result = trainingSheetService.buscar(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Treino de força", result.getDescription());
        assertEquals(student, result.getStudent());
        assertEquals(trainer, result.getTrainer());
        assertEquals(updater, result.getUpdateBy());

        verify(trainingSheetRepository).findById(1);
    }

    @Test
    void shouldThrowExceptionWhenTrainingSheetNotFoundOnSearch() {
        when(trainingSheetRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> trainingSheetService.buscar(1));

        assertEquals("Folha de treino não encontrada.", exception.getMessage());

        verify(trainingSheetRepository).findById(1);
    }

    @Test
    void shouldEditTrainingSheetAndVerifyFields() {
        when(trainingSheetRepository.findById(1)).thenReturn(Optional.of(trainingSheet));
        when(profileRepository.findByKeycloakId(student.getKeycloakId())).thenReturn(Optional.of(student));
        when(profileRepository.findByKeycloakId(trainer.getKeycloakId())).thenReturn(Optional.of(trainer));
        when(profileRepository.findByKeycloakId(updater.getKeycloakId())).thenReturn(Optional.of(updater));
        when(exerciseRepository.findAllById(trainingSheetDTO.exerciseIds()))
                .thenReturn(Arrays.asList(new Exercise(), new Exercise(), new Exercise()));

        when(trainingSheetRepository.save(any(TrainingSheet.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(trainingSheetMapper.toDTO(any(TrainingSheet.class))).thenReturn(trainingSheetDTO);

        TrainingSheetDTO result = trainingSheetService.editar(1, trainingSheetDTO);

        assertNotNull(result);
        assertEquals(trainingSheetDTO.description(), result.description());

        // Capturando o TrainingSheet salvo para verificar os campos
        ArgumentCaptor<TrainingSheet> captor = ArgumentCaptor.forClass(TrainingSheet.class);
        verify(trainingSheetRepository).save(captor.capture());
        TrainingSheet updatedSheet = captor.getValue();

        assertEquals("Treino de força", updatedSheet.getDescription());
        assertEquals(student, updatedSheet.getStudent());
        assertEquals(trainer, updatedSheet.getTrainer());
        assertEquals(updater, updatedSheet.getUpdateBy());
        assertEquals(3, updatedSheet.getExercises().size());

        verify(trainingSheetRepository).save(any(TrainingSheet.class));
    }

    @Test
    void shouldThrowExceptionWhenExerciseIdsAreInvalidOnEdit() {
        Integer id = 1;

        when(trainingSheetRepository.findById(id)).thenReturn(Optional.of(trainingSheet));
        when(profileRepository.findByKeycloakId(student.getKeycloakId())).thenReturn(Optional.of(student));
        when(profileRepository.findByKeycloakId(trainer.getKeycloakId())).thenReturn(Optional.of(trainer));
        when(profileRepository.findByKeycloakId(updater.getKeycloakId())).thenReturn(Optional.of(updater));

        // Simulando retorno incompleto de exercícios (2 ao invés de 3)
        when(exerciseRepository.findAllById(trainingSheetDTO.exerciseIds())).thenReturn(Arrays.asList(new Exercise(), new Exercise()));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> trainingSheetService.editar(id, trainingSheetDTO));

        assertEquals("Alguns IDs de exercícios são inválidos.", exception.getMessage());

        verify(trainingSheetRepository).findById(id);
        verify(profileRepository).findByKeycloakId(student.getKeycloakId());
        verify(profileRepository).findByKeycloakId(trainer.getKeycloakId());
        verify(profileRepository).findByKeycloakId(updater.getKeycloakId());
        verify(exerciseRepository).findAllById(trainingSheetDTO.exerciseIds());
    }
}
