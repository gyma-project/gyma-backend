package com.gyma.gyma.controller.dto;

import com.gyma.gyma.model.enums.MuscleGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "Exercício")
public record ExerciseDTO(
    @NotBlank(message = "O exercício não pode ser vazio.")
    String name,

    @NotNull(message = "O grupo muscular não pode ser nulo.")
    MuscleGroup muscleGroup,

    @Positive(message = "O número de vezes deve ser positivo.")
    @NotNull(message = "O número de vezes não pode ser nulo.")
    @Min(value = 1, message = "Número de vezes deve ser no mínimo 1.")
    Integer amount,

    @Positive(message = "O número de repetições deve ser positivo.")
    @NotNull(message = "O número de repetições não pode ser nulo.")
    @Min(value = 1, message = "Número de vezes deve ser no mínimo 1.")
    Integer repetition
) {
}
