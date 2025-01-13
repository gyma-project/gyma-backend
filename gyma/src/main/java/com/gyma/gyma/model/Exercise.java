package com.gyma.gyma.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gyma.gyma.model.enums.MuscleGroup;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "exercise", schema = "public")
@Getter
@Setter
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "muscle_group", nullable = false)
    private MuscleGroup muscleGroup;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "repetition", nullable = false)
    private Integer repetition;

    @ManyToMany(mappedBy = "exercises")
    @JsonIgnore
    private List<TrainingSheet> trainingSheets;
}
