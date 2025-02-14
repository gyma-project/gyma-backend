package com.gyma.gyma.model;

import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingReport;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TrainingReportTest {

    @Test
    void testTrainingReportGettersAndSetters() {
        TrainingReport report = new TrainingReport();
        Profile student = new Profile();
        Profile trainer = new Profile();

        report.setId(1);
        report.setStudent(student);
        report.setTrainer(trainer);
        report.setHeight(1.80);
        report.setWeight(75.0);
        report.setDescription("Great progress!");
        report.setCreatedAt(LocalDateTime.now());
        report.setUpdatedAt(LocalDateTime.now());

        assertEquals(1, report.getId());
        assertEquals(student, report.getStudent());
        assertEquals(trainer, report.getTrainer());
        assertEquals(1.80, report.getHeight());
        assertEquals(75.0, report.getWeight());
        assertEquals("Great progress!", report.getDescription());
        assertNotNull(report.getCreatedAt());
        assertNotNull(report.getUpdatedAt());
    }
}
