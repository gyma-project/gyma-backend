package com.gyma.gyma.repository;


import com.gyma.gyma.model.Day;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public interface TrainingTimeRepository extends JpaRepository<TrainingTime, Integer>, JpaSpecificationExecutor<TrainingTime> {
    boolean existsByDay(Day day);
    boolean existsByDayAndStartTimeBetween(Day day, LocalTime startTime, LocalTime endTime);
    boolean existsByDayAndStartTime(Day day, LocalTime startTime);

    Optional<TrainingTime> findByTrainer_KeycloakId(UUID trainerId);
}
