package com.gyma.gyma.controller.dto;

import com.gyma.gyma.model.enums.MuscleGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(name = "Exercício")
public record ExerciseDTO(
    @NotBlank(message = "O exercício não pode ser vazio.")
    String name,

    @NotBlank(message = "O grupo de músculo não pode ser vazio.")
    MuscleGroup muscleGroup,

    @Positive(message = "O número de vezes deve ser positivo.")
    @NotBlank(message = "O número de vezes não pode ser vazio.")
    @Size(min = 1, message = "Número de vezes fora do tamanho padrão.")
    Integer amount,

    @Positive(message = "O número de repetições deve ser positivo.")
    @NotBlank(message = "O número de repetições não pode ser vazio.")
    @Size(min = 1, message = "Número de repetições fora do tamanho padrão.")
    Integer repetition
) {
}
