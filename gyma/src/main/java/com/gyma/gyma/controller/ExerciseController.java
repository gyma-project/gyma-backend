package com.gyma.gyma.controller;


import com.gyma.gyma.controller.dto.ExerciseDTO;
import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.model.enums.MuscleGroup;
import com.gyma.gyma.service.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exercises")
@Tag(name = "Exercícios", description = "Gerenciamento de exercícios.")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @Operation(summary = "Listar", description = "Listar os exercícios.")
    public Page<Exercise> listar(
            @RequestParam(required = false) MuscleGroup muscleGroup,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer amount,
            @RequestParam(required = false) Integer repetition,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer size
    ){
        return exerciseService.listar(
                muscleGroup,
                name,
                amount,
                repetition,
                pageNumber,
                size
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @Operation(summary = "Criar", description = "Criar exercício.")
    public ResponseEntity<ExerciseDTO> criar(@Valid @RequestBody ExerciseDTO exerciseDTO){
        return ResponseEntity.ok(exerciseService.salvar(exerciseDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @Operation(summary = "Editar", description = "Editar exercício.")
    public ResponseEntity<ExerciseDTO> editar(
            @PathVariable Integer id,
            @RequestBody ExerciseDTO exerciseDTO
    ){
        return ResponseEntity.ok(exerciseService.editar(id, exerciseDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @Operation(summary = "Deletar", description = "Criar exercício.")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        exerciseService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
