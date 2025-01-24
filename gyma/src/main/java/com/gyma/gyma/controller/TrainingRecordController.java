package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.service.TrainingRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-records")
@Tag(name = "Agendamentos", description = "Gerenciamento de agendamentos.")
public class TrainingRecordController {

    @Autowired
    private TrainingRecordService trainingRecordService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @PostMapping
    @Operation(summary = "Criar", description = "Criar um agendamento.")
    public ResponseEntity<TrainingRecordDTO> criar(@RequestBody TrainingRecordDTO trainingRecordDTO) {
        TrainingRecordDTO criado = trainingRecordService.agendar(trainingRecordDTO);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }

    @GetMapping("/relatorio")
    @Operation(summary = "Gerar relatório", description = "Criar um relatório.")
    public String criarRelatorio() {
        return trainingRecordService.criarRelatorio();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @Operation(summary = "Listar", description = "Listar todos agendamentos.")
    public ResponseEntity<List<TrainingRecord>> listarTodos() {
        List<TrainingRecord> registros = trainingRecordService.listarTodos();
        return ResponseEntity.ok(registros);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar", description = "Deletar um agendamento.")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        trainingRecordService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
