package com.gyma.gyma.repository;

import com.gyma.gyma.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {
    long countByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
    @Query("SELECT SUM(t.price) FROM Transaction t WHERE t.createdAt BETWEEN :startDate AND :endDate")
    BigDecimal sumPriceByCreatedAtBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT t.category FROM Transaction t WHERE t.createdAt BETWEEN :startDate AND :endDate GROUP BY t.category ORDER BY COUNT(t.category) DESC LIMIT 1")
    String findMostFrequentCategory(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
