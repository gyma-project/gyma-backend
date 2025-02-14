package com.gyma.gyma.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTimeTest {

    @Test
    void testTrainingTimeGettersAndSetters() {
        TrainingTime time = new TrainingTime();
        Profile trainer = new Profile();

        time.setId(1);
        time.setStartTime(LocalTime.of(9, 0));
        time.setEndTime(LocalTime.of(10, 0));
        time.setStudentsLimit(10);
        time.setTrainer(trainer);
        time.setActive(true);
        time.setCreatedAt(LocalDateTime.now());
        time.setUpdatedAt(LocalDateTime.now());

        assertEquals(1, time.getId());
        assertEquals(LocalTime.of(9, 0), time.getStartTime());
        assertEquals(LocalTime.of(10, 0), time.getEndTime());
        assertEquals(10, time.getStudentsLimit());
        assertEquals(trainer, time.getTrainer());
        assertTrue(time.getActive());
        assertNotNull(time.getCreatedAt());
        assertNotNull(time.getUpdatedAt());
    }
}
