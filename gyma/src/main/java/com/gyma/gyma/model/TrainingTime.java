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

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_the_week", nullable = false)
    private DayOfTheWeek dayOfTheWeek;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "students_limit", nullable = false)
    private Integer studentsLimit;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id", nullable = false)
    private Trainer trainer;

    @Column(nullable = false)
    private Boolean active;

    @CreatedDate
    @Column(name="data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name="data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name="id_usuario")
    private Integer idUsuario;
}
