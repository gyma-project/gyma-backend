package com.gyma.gyma.repository;


import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRecordRepository extends JpaRepository<TrainingRecord, Integer> {

}
