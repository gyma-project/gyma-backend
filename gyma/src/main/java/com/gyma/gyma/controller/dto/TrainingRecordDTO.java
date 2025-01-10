package com.gyma.gyma.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public record TrainingRecordDTO(
        Integer trainingTimeId,
        String student,
        String trainer,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {

}
