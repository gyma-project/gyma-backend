package com.gyma.gyma.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(name = "Agendamento")
public record TrainingRecordDTO(
        @NotNull(message = "ID do 'trainingTimeId' não pode ser nulo.")
        Integer trainingTimeId,
        @NotNull(message = "ID do student não pode ser nulo.")
        String student,
        @NotNull(message = "ID do treinador não pode ser nulo.")
        String trainer,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {

}
