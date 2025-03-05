package com.gyma.gyma.specificiations;

import com.gyma.gyma.model.TrainingSheet;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

public class TrainingSheetSpecification {

    public static Specification<TrainingSheet> byName(String name) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(name)) {
                return null;
            }
            return builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<TrainingSheet> byStudentKeycloakId(UUID studentKeycloakId) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(studentKeycloakId)) {
                return null;
            }
            return builder.equal(root.get("student").get("keycloakId"), studentKeycloakId);
        };
    }

    public static Specification<TrainingSheet> byTrainerKeycloakId(UUID trainerKeycloakId) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(trainerKeycloakId)) {
                return null;
            }
            return builder.equal(root.get("trainer").get("keycloakId"), trainerKeycloakId);
        };
    }

    public static Specification<TrainingSheet> byUpdateBy(UUID updateByUuid) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(updateByUuid)) {
                return null;
            }
            return builder.equal(root.get("updateBy").get("keycloakId"), updateByUuid);
        };
    }
}
