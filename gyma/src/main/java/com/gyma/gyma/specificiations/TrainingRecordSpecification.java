package com.gyma.gyma.specificiations;

import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.model.TrainingTime;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TrainingRecordSpecification {
    public static Specification<TrainingRecord> byTrainingTime(Integer trainingTime) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(trainingTime)) {
                return null;
            }
            return builder.equal(root.get("trainingTime").get("id"), trainingTime);
        };
    }

    public static Specification<TrainingRecord> byStudent(UUID student) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(student)) {
                return null;
            }
            return builder.equal(root.get("student").get("keycloakId"), student);
        };
    }

    public static Specification<TrainingRecord> byTrainer(UUID trainer) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(trainer)) {
                return null;
            }
            return builder.equal(root.get("trainer").get("keycloakId"), trainer);
        };
    }

    public static Specification<TrainingRecord> byCreatedAt(LocalDate createdAt) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(createdAt)) {
                return null;
            }
            return builder.equal(root.get("createdAt"), createdAt);
        };
    }

    public static Specification<TrainingRecord> byUpdatedAt(LocalDate updatedAt) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(updatedAt)) {
                return null;
            }
            return builder.equal(root.get("updateAt"), updatedAt);
        };
    }

    public static Specification<TrainingRecord> byCreatedAtBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(startDate) || ObjectUtils.isEmpty(endDate)) {
                return null;
            }
            return builder.between(root.get("createdAt"), startDate, endDate);
        };
    }

    public static Specification<TrainingRecord> byUpdatedAtBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(startDate) || ObjectUtils.isEmpty(endDate)) {
                return null;
            }
            return builder.between(root.get("updateAt"), startDate, endDate);
        };
    }
}
