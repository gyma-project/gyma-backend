package com.gyma.gyma.model.enums;

import lombok.Getter;

@Getter
public enum MuscleGroup {
    CHEST("Peito"),
    BACK("Costas"),
    ARMS("Braços"),
    SHOULDERS("Ombros"),
    LEGS("Pernas"),
    ABDOMINALS("Abdominais"),
    GLUTES("Glúteos");

    private final String description;

    MuscleGroup(String description) {
        this.description = description;
    }

}