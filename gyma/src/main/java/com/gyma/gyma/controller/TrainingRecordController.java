package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.service.TrainingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-records")
public class TrainingRecordController {

    @Autowired
    private TrainingRecordService trainingRecordService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<TrainingRecordDTO> criar(@RequestBody TrainingRecordDTO trainingRecordDTO) {
        TrainingRecordDTO criado = trainingRecordService.criarRegistro(trainingRecordDTO);
        return new ResponseEntity<>(criado, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TrainingRecord>> listarTodos() {
        List<TrainingRecord> registros = trainingRecordService.listarTodos();
        return ResponseEntity.ok(registros);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        trainingRecordService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
