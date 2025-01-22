package com.gyma.gyma.service;

import com.gyma.gyma.mappers.ProfileMapper;
import com.gyma.gyma.mappers.TrainingTimeMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.repository.DayRepository;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.parameters.P;

import java.time.LocalTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    void setUp() {
        UUID keycloakId = UUID.randomUUID();
        trainer = new Profile(
                1,
                "johndoe",
                "johndoe@example.com",
                "John",
                "Doe",
                keycloakId
        );
        trainingTime = new TrainingTime(
                1,
                null,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                20,
                trainer,
                true,
                null,
                null,
                trainer
        );
    }

    @Test
    @DisplayName("Deve retornar uma lista paginada de horários de treino")
    public void deveRetornarUmaListaPaginadaDeTrainingTime() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<TrainingTime> trainingTimePage = new PageImpl<>(Collections.singletonList(trainingTime), pageRequest, 1);

        when(trainingTimeRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(trainingTimePage);

        Page<TrainingTime> result = trainingTimeService.listarTodos(
                LocalTime.of(9, 0),
                LocalTime.of(10, 0),
                20,
                null,
                null,
                true,
                null,
                0,
                10
        );
        assertEquals(
                1,
                result.getTotalElements()
        );
        assertEquals(
                trainingTime,
                result.getContent().get(0)
        );
    }


}
