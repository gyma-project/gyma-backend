package com.gyma.gyma.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="appointment")
@Getter
@Setter
public class TrainingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "training_time_id", nullable = false)
    private TrainingTime trainingTime;

    //@ManyToOne
    //@JoinColumn(name = "student_id", nullable = false)
    //private Student student;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

}
