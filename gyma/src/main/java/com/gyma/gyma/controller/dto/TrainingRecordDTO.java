package com.gyma.gyma.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
