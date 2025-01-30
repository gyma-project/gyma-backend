package com.gyma.gyma.controller.specificiations;

import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.model.enums.MuscleGroup;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class ExerciseSpecifications {
    public static Specification<Exercise> byMuscleGroup(MuscleGroup muscleGroup) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(muscleGroup)) {
                return null;
            }
            return builder.equal(root.get("muscleGroup"), muscleGroup);
        };
    }

    public static Specification<Exercise> byName(String name) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(name)) {
                return null;
            }
            return builder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<Exercise> byAmount(Integer amount) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(amount)) {
                return null;
            }
            return builder.equal(root.get("amount"), amount);
        };
    }

    public static Specification<Exercise> byRepetition(Integer repetition) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(repetition)) {
                return null;
            }
            return builder.equal(root.get("repetition"), repetition);
        };
    }
}
