package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.service.TrainingTimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("training-times")
@Tag(name = "Horário de Treino", description = "Gerenciamento de treinos.")
public class TrainingTimeController {

    @Autowired
    private TrainingTimeService trainingTimeService;


    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @GetMapping
    @Operation(summary = "Listar", description = "Listar todos horários de treino.")
    public ResponseEntity<List<TrainingTime>> getAllTrainingTimes() {
        List<TrainingTime> trainingTimes = trainingTimeService.listarTodos();
        return ResponseEntity.ok(trainingTimes);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Buscar por ID horário de treino.")
    public ResponseEntity<TrainingTimeDTO> getTrainingTimeById(@PathVariable Integer id) {
        TrainingTimeDTO trainingTime = trainingTimeService.buscarPorId(id);
        return ResponseEntity.ok(trainingTime);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @PatchMapping("/{id}")
    @Operation(summary = "Editar", description = "Editar parcialmente horário de treino.")
    public ResponseEntity<TrainingTime> editarParcialmente(
            @PathVariable Integer id,
            @RequestBody TrainingTimeDTO trainingTimeDTO
    ){
        TrainingTime trainingTime = trainingTimeService.editar(id, trainingTimeDTO);
        return ResponseEntity.ok(trainingTime);
    }

}
