package com.gyma.gyma.repository;

import com.gyma.gyma.model.TrainingSheet;
import com.gyma.gyma.model.TrainingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TrainingSheetRepository extends JpaRepository<TrainingSheet, Integer>, JpaSpecificationExecutor<TrainingSheet> {
}
