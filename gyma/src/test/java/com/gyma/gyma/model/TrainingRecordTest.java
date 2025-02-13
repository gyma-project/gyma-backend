package com.gyma.gyma.model;

import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.model.TrainingTime;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TrainingRecordTest {

    @Test
    void testTrainingRecordGettersAndSetters() {
        TrainingRecord record = new TrainingRecord();
        TrainingTime trainingTime = new TrainingTime();
        Profile student = new Profile();
        Profile trainer = new Profile();

        record.setId(1);
        record.setTrainingTime(trainingTime);
        record.setStudent(student);
        record.setTrainer(trainer);
        record.setCreatedAt(LocalDate.now());
        record.setUpdateAt(LocalDate.now());

        assertEquals(1, record.getId());
        assertEquals(trainingTime, record.getTrainingTime());
        assertEquals(student, record.getStudent());
        assertEquals(trainer, record.getTrainer());
        assertNotNull(record.getCreatedAt());
        assertNotNull(record.getUpdateAt());
    }
}
