package com.gyma.gyma.specificiations;

import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.model.enums.DayOfTheWeek;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalTime;
import java.util.UUID;

public class TrainingTimeSpecification {
    public static Specification<TrainingTime> byStartTime(LocalTime startTime) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(startTime)) {
                return null;
            }
            return builder.equal(root.get("startTime"), startTime);
        };
    }

    public static Specification<TrainingTime> byEndTime(LocalTime endTime) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(endTime)) {
                return null;
            }
            return builder.equal(root.get("endTime"), endTime);
        };
    }

    public static Specification<TrainingTime> byActiveStatus(Boolean active) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(active)) {
                return null;
            }
            return builder.equal(root.get("active"), active);
        };
    }

    public static Specification<TrainingTime> byTrainerKeycloakId(UUID traineUuid) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(traineUuid)) {
                return null;
            }
            return builder.equal(root.get("trainer").get("keycloakId"), traineUuid);
        };
    }

    public static Specification<TrainingTime> byDayName(String dayName) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(dayName)) {
                return null;
            }
            try {
                DayOfTheWeek dayEnum = DayOfTheWeek.valueOf(dayName.toUpperCase());
                return builder.equal(root.get("day"), dayEnum);
            } catch (IllegalArgumentException e) {
                return null;
            }
        };
    }


    public static Specification<TrainingTime> byStudentLimit(Integer studentLimit) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(studentLimit)) {
                return null;
            }
            return builder.equal(root.get("studentsLimit"), studentLimit);
        };
    }

    public static Specification<TrainingTime> byUpdateBy(UUID updateByUuid) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(updateByUuid)) {
                return null;
            }
            return builder.equal(root.get("updateBy").get("keycloakId"), updateByUuid);
        };
    }
}
