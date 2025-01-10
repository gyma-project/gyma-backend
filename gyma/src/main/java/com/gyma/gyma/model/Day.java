package com.gyma.gyma.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gyma.gyma.model.enums.DayOfTheWeek;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "day", schema = "public")
@Getter
@Setter
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true)
    private DayOfTheWeek name;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "day_training_time",
            joinColumns = @JoinColumn(name = "day_id"),
            inverseJoinColumns = @JoinColumn(name = "training_time_id")
    )
    @JsonBackReference
    private List<TrainingTime> trainingTimes = new ArrayList<>();

    @Override
    public String toString() {
        return name.name();
    }

}
