package com.gyma.gyma.controller.dto;

import com.gyma.gyma.model.Day;
import com.gyma.gyma.model.TrainingTime;

import java.time.LocalTime;
import java.util.UUID;

public record TrainingTimeDTO(
        Integer studentsLimit,
        UUID trainerId,
        UUID idUsuario,
        Boolean active
) {

    public TrainingTime mapearParaTraining(){
        TrainingTime training = new TrainingTime();
        training.setStudentsLimit(this.studentsLimit);
        training.setIdUsuario(this.idUsuario);
        training.setTrainerId(this.trainerId);
        training.setActive(this.active);
        return training;
    }

}
