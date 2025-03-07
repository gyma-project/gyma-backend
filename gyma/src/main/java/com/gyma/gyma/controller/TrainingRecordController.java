package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.service.TrainingRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/training-records")
@Tag(name = "Agendamentos", description = "Gerenciamento de agendamentos.")
public class TrainingRecordController {

    @Autowired
    private TrainingRecordService trainingRecordService;

    @PostMapping
    @Operation(summary = "Criar", description = "Criar um agendamento.")
    public ResponseEntity<TrainingRecordDTO> criar(@RequestBody TrainingRecordDTO trainingRecordDTO) {
        TrainingRecordDTO criado = trainingRecordService.agendar(trainingRecordDTO);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }

    @GetMapping("/relatorio")
    @Operation(summary = "Gerar relatório", description = "Criar um relatório.")
    public String criarRelatorio() throws IOException {
        return trainingRecordService.criarRelatorio();
    }

    @GetMapping
    @Operation(summary = "Listar", description = "Listar todos agendamentos.")
    public ResponseEntity<Page<TrainingRecord>> listarTodos(
            @RequestParam(required = false) Integer trainingTimeId,
            @RequestParam(required = false) UUID studentId,
            @RequestParam(required = false) UUID trainerId,
            @RequestParam(required = false) LocalDate createdAt,
            @RequestParam(required = false) LocalDate updatedAt,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer size
    ) {
        return ResponseEntity.ok(trainingRecordService.listarTodos(
                trainingTimeId,
                studentId,
                trainerId,
                createdAt,
                updatedAt,
                startDate,
                endDate,
                pageNumber,
                size
        ));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar", description = "Deletar um agendamento.")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        trainingRecordService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
