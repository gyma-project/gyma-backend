package com.gyma.gyma.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trainer", schema = "public")
public class Trainer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;
}
