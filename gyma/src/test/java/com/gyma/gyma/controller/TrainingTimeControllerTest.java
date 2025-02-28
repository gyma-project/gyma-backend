package com.gyma.gyma.controller;

import com.gyma.gyma.config.SecurityTestConfig;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.service.TrainingTimeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@WebMvcTest(controllers = TrainingTimeController.class)
@Import(SecurityTestConfig.class)
@ActiveProfiles("test")
class TrainingTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TrainingTimeService trainingTimeService;

    @InjectMocks
    private TrainingTimeController trainingTimeController;

    @BeforeEach
    void setUp() {
        // Aqui o setup é opcional, porque o Spring já gerencia o contexto.
    }

    @Test
    void testGetTrainingTimeById() throws Exception {
        TrainingTime trainingTime = new TrainingTime();
        trainingTime.setId(1);

        when(trainingTimeService.buscarPorId(1)).thenReturn(trainingTime);

        mockMvc.perform(get("/api/v1/training-times/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(trainingTimeService, times(1)).buscarPorId(1);
    }

}
