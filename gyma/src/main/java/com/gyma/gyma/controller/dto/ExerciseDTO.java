package com.gyma.gyma.controller.dto;

import com.gyma.gyma.model.enums.MuscleGroup;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Exercício")
public record ExerciseDTO(
    String name,
    MuscleGroup muscleGroup,
    Integer amount,
    Integer repetition
) {
}
