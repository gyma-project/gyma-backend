package com.gyma.gyma.repository;


import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.model.TrainingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;


public interface TrainingRecordRepository extends JpaRepository<TrainingRecord, Integer>, JpaSpecificationExecutor<TrainingRecord> {
    long countByTrainingTimeAndCreatedAt(TrainingTime trainingTime, LocalDate createdAt);

    @Query("SELECT COUNT(t) FROM TrainingRecord t WHERE t.createdAt BETWEEN :startOfWeek AND :endOfWeek")
    long countByCreatedAtBetween(@Param("startOfWeek") LocalDate startOfWeek, @Param("endOfWeek") LocalDate endOfWeek);
}
