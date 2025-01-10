package com.gyma.gyma.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name="appointment")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class TrainingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "training_time_id", nullable = false)
    private TrainingTime trainingTime;

    @Column(name = "keycloak_user_id", nullable = false, length = 255)
    private String student;

    @Column(name = "trainer_keycloak_user_id", nullable = false, length = 255)
    private String trainer;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updateAt;

}
