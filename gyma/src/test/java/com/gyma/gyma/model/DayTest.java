package com.gyma.gyma.model;

import com.gyma.gyma.model.enums.DayOfTheWeek;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DayTest {

    private Day day;

    @BeforeEach
    void setUp() {
        day = new Day();
        day.setId(1);
        day.setName(DayOfTheWeek.MONDAY);
        day.setActive(true);
    }

    @Test
    void testDayInitialization() {
        assertNotNull(day);
        assertEquals(1, day.getId());
        assertEquals(DayOfTheWeek.MONDAY, day.getName());
        assertTrue(day.getActive());
        assertEquals(0, day.getTrainingTimes().size());
    }

    @Test
    void testAddTrainingTime() {
        TrainingTime trainingTime = new TrainingTime();
        trainingTime.setId(1);
        trainingTime.setActive(true);

        List<TrainingTime> trainingTimes = new ArrayList<>();
        trainingTimes.add(trainingTime);

        day.setTrainingTimes(trainingTimes);

        assertNotNull(day.getTrainingTimes());
        assertEquals(1, day.getTrainingTimes().size());
        assertEquals(trainingTime, day.getTrainingTimes().get(0));
    }

    @Test
    void testSetActive() {
        day.setActive(false);
        assertFalse(day.getActive());
    }

    @Test
    void testToString() {
        assertEquals("MONDAY", day.toString());
    }
}
