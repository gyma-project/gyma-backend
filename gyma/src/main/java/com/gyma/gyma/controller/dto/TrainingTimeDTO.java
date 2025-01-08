package com.gyma.gyma.controller.dto;

import com.gyma.gyma.model.enums.DayOfTheWeek;
import com.gyma.gyma.model.Trainer;
import com.gyma.gyma.model.TrainingTime;

import java.time.LocalTime;

public record TrainingTimeDTO(
        DayOfTheWeek dayOfTheWeek,
        LocalTime startTime,
        LocalTime endTime,
        Integer studentsLimit,
        Integer trainerId,
        Boolean active
) {

    public TrainingTime mapearParaTraining(Trainer trainer){
        TrainingTime training = new TrainingTime();
        training.setDayOfTheWeek(this.dayOfTheWeek);
        training.setStartTime(this.startTime);
        training.setEndTime(this.endTime);
        training.setStudentsLimit(this.studentsLimit);
        training.setTrainer(trainer);
        training.setActive(this.active);
        return training;
    }

}
