package com.gyma.gyma.repository;

import com.gyma.gyma.model.enums.DayOfTheWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {
    Optional<Day> findByName(DayOfTheWeek name);
}
