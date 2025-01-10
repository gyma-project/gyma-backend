package com.gyma.gyma.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gyma.gyma.model.enums.DayOfTheWeek;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

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

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<TrainingTime> trainingTimes;

    @Override
    public String toString() {
        return name.name();
    }

}
