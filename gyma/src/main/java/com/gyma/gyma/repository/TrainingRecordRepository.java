package com.gyma.gyma.repository;


import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.model.TrainingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;


public interface TrainingRecordRepository extends JpaRepository<TrainingRecord, Integer>, JpaSpecificationExecutor<TrainingRecord> {
    long countByTrainingTimeAndCreatedAt(TrainingTime trainingTime, LocalDate createdAt);
}
