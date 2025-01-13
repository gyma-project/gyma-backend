package com.gyma.gyma.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gyma.gyma.model.enums.DayOfTheWeek;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "day", schema = "public")
@Getter
@Setter
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



}
