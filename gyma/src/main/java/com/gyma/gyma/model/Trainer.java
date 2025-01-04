package com.gyma.gyma.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "trainer", schema = "public")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Trainer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "cpf_treinador", length = 100, nullable = false)
    private String cpfTreinador;

    @CreatedDate
    @Column(name="data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name="data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name="id_usuario")
    private Integer idUsuario;
}
