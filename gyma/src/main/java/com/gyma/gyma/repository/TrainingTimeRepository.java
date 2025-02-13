package com.gyma.gyma.repository;


import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.model.enums.DayOfTheWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public interface TrainingTimeRepository extends JpaRepository<TrainingTime, Integer>, JpaSpecificationExecutor<TrainingTime> {
    boolean existsByDay(DayOfTheWeek day);
    boolean existsByDayAndStartTimeBetween(DayOfTheWeek day, LocalTime startTime, LocalTime endTime);
    boolean existsByDayAndStartTime(DayOfTheWeek day, LocalTime startTime);

    Optional<TrainingTime> findByTrainer_KeycloakId(UUID trainerId);
}
