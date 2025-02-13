package com.gyma.gyma.model;

import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.model.enums.MuscleGroup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    @Test
    void testExerciseGettersAndSetters() {
        Exercise exercise = new Exercise();
        exercise.setId(1);
        exercise.setName("Push-up");
        exercise.setAmount(3);
        exercise.setRepetition(15);
        exercise.setMuscleGroup(MuscleGroup.CHEST);

        assertEquals(1, exercise.getId());
        assertEquals("Push-up", exercise.getName());
        assertEquals(3, exercise.getAmount());
        assertEquals(15, exercise.getRepetition());
        assertEquals(MuscleGroup.CHEST, exercise.getMuscleGroup());
    }
}
