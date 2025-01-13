package com.gyma.gyma.controller.dto;

import java.util.List;
import java.util.UUID;

public record TrainingSheetDTO(
    UUID student,
    UUID trainer,
    String description,
    List<Integer> exerciseIds,
    UUID idUsuario
) {
}