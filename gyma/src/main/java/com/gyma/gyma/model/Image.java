package com.gyma.gyma.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_object", nullable = false, length = 255, unique = true)
    private String idObject;

    @OneToOne(mappedBy = "image", fetch = FetchType.EAGER)
    private Profile profile;
}
