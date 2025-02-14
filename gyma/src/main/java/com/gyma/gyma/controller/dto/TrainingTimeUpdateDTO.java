package com.gyma.gyma.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TrainingTimeUpdateDTO {

    @NotNull(message = "O campo 'active' não pode ser nulo.")
    private Boolean active;

    @NotNull(message = "O campo 'trainerId' não pode ser nulo.")
    private UUID trainerId;

    @Min(value = 1, message = "O limite de alunos deve ser maior que 0.")
    @Max(value = 100, message = "O limite de alunos não pode ser superior a 100.")
    private Integer studentsLimit;

    @NotNull(message = "O campo 'updateBy' não pode ser nulo.")
    private UUID updateBy;
}
