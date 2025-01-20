package com.gyma.gyma.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(name = "Ficha de treino")
public record TrainingSheetDTO(
    UUID student,
    UUID trainer,
    String description,
    List<Integer> exerciseIds,
    UUID updateBy
) {
}