package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.TrainerDTO;
import com.gyma.gyma.model.Trainer;
import com.gyma.gyma.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trainers")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @PostMapping
    public ResponseEntity<Trainer> salvar(TrainerDTO trainerDTO){
        // Criar validações (CPF duplicado, Email duplicado)
    }

}
