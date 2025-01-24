package com.gyma.gyma.model.tests;

import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingSheet;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TrainingSheetTest {

    @Test
    void testTrainingSheetGettersAndSetters() {
        TrainingSheet sheet = new TrainingSheet();
        Profile student = new Profile();
        Profile trainer = new Profile();
        Exercise exercise = new Exercise();

        sheet.setId(1);
        sheet.setStudent(student);
        sheet.setTrainer(trainer);
        sheet.setExercises(Collections.singletonList(exercise));
        sheet.setDescription("Full-body workout");
        sheet.setCreatedAt(LocalDateTime.now());
        sheet.setUpdatedAt(LocalDateTime.now());

        assertEquals(1, sheet.getId());
        assertEquals(student, sheet.getStudent());
        assertEquals(trainer, sheet.getTrainer());
        assertEquals(1, sheet.getExercises().size());
        assertEquals("Full-body workout", sheet.getDescription());
        assertNotNull(sheet.getCreatedAt());
        assertNotNull(sheet.getUpdatedAt());
    }
}
