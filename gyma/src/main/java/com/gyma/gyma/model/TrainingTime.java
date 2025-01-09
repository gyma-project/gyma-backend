package com.gyma.gyma.model;

import com.gyma.gyma.model.enums.DayOfTheWeek;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name="training_time", schema = "public")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class TrainingTime {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "day_id", nullable = false)
    private Day day;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "students_limit", nullable = false)
    private Integer studentsLimit;

    @Column(name = "trainer_id", nullable = false)
    private UUID trainerId;

    @Column(nullable = false)
    private Boolean active;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "edit_by", nullable = false)
    private UUID idUsuario;
}
