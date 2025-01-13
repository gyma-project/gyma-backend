package com.gyma.gyma.controller.dto;

import com.gyma.gyma.model.enums.MuscleGroup;

public record ExerciseDTO(
    String name,
    MuscleGroup muscleGroup,
    Integer amount,
    Integer repetition
) {
}
