package com.gyma.gyma.repository;


import com.gyma.gyma.model.Day;
import com.gyma.gyma.model.TrainingTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface TrainingTimeRepository extends JpaRepository<TrainingTime, Integer> {
    boolean existsByDay(Day day);
    boolean existsByDayAndStartTimeBetween(Day day, LocalTime startTime, LocalTime endTime);

    // Verificar se já existe um horário específico para o dia e horário de início
    boolean existsByDayAndStartTime(Day day, LocalTime startTime);
}
