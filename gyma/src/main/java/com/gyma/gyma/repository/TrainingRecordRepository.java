package com.gyma.gyma.repository;


import com.gyma.gyma.model.TrainingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRecordRepository extends JpaRepository<TrainingRecord, Integer> {
}
