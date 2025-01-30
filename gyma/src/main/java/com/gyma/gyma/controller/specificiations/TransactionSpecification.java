package com.gyma.gyma.controller.specificiations;

import com.gyma.gyma.model.Transaction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionSpecification {

    public static Specification<Transaction> bySenderId(UUID senderId) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(senderId)) {
                return null;
            }
            return builder.equal(root.get("sender").get("keycloakId"), senderId);
        };
    }

    public static Specification<Transaction> byCreatedById(UUID createdById) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(createdById)) {
                return null;
            }
            return builder.equal(root.get("createdBy").get("keycloakId"), createdById);
        };
    }

    public static Specification<Transaction> byPriceGreaterThan(BigDecimal price) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(price)) {
                return null;
            }
            return builder.greaterThan(root.get("price"), price);
        };
    }

    public static Specification<Transaction> byPriceLessThan(BigDecimal price) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(price)) {
                return null;
            }
            return builder.lessThan(root.get("price"), price);
        };
    }

    public static Specification<Transaction> byDescription(String description) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(description)) {
                return null;
            }
            return builder.like(builder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
        };
    }

    public static Specification<Transaction> byCreatedAt(LocalDate createdAt) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(createdAt)) {
                return null;
            }
            return builder.greaterThanOrEqualTo(root.get("createdAt"), createdAt);
        };
    }

    public static Specification<Transaction> byUpdatedAt(LocalDate updatedAt) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(updatedAt)) {
                return null;
            }
            return builder.lessThanOrEqualTo(root.get("updatedAt"), updatedAt);
        };
    }

    public static Specification<Transaction> byUpdateById(UUID updateById) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(updateById)) {
                return null;
            }
            return builder.equal(root.get("updateBy").get("keycloakId"), updateById);
        };
    }
}
