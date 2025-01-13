package com.gyma.gyma.controller;


import com.gyma.gyma.controller.dto.ExerciseDTO;
import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.service.ExerciseService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<Exercise>> listar(){
        return ResponseEntity.ok(exerciseService.listar());
    }

    @PostMapping
    public ResponseEntity<ExerciseDTO> criar(@RequestBody ExerciseDTO exerciseDTO){
        return ResponseEntity.ok(exerciseService.salvar(exerciseDTO));
    }


}
