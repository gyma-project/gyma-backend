package com.gyma.gyma.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="training_sheet", schema = "public")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class TrainingSheet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "keycloak_user_id", nullable = false, length = 255)
    private UUID student;

    @Column(name = "trainer_keycloak_user_id", nullable = false, length = 255)
    private UUID trainer;

    @ManyToMany
    @JoinTable(
            name="training_sheet_exercise",
            joinColumns = @JoinColumn(name="training_sheet_id"),
            inverseJoinColumns = @JoinColumn(name="exercise_id")
    )
    private List<Exercise> exercises;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "edit_by", nullable = false, length = 255)
    private UUID idUsuario;

}
