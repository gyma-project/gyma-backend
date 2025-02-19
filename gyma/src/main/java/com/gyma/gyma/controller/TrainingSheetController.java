package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.TrainingSheetDTO;
import com.gyma.gyma.model.TrainingSheet;
import com.gyma.gyma.service.TrainingSheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/training-sheets")
@Tag(name = "Ficha de Treino", description = "Gerenciamento de fichas de treino.")
public class TrainingSheetController {

    @Autowired
    private TrainingSheetService trainingSheetService;

    @GetMapping
    @Operation(summary = "Listar", description = "Listar todas fichas de treino.")
    public ResponseEntity<List<TrainingSheet>> listar(){
        return ResponseEntity.ok(trainingSheetService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar", description = "Buscar uma ficha de treino.")
    public ResponseEntity<TrainingSheet> buscar(@PathVariable Integer id){
        return ResponseEntity.ok(trainingSheetService.buscar(id));
    }

    @PostMapping
    @Operation(summary = "Salvar", description = "Salvar uma ficha de treino.")
    public ResponseEntity<TrainingSheetDTO> salvar(
            @RequestBody TrainingSheetDTO trainingSheetDTO
    ){
        return ResponseEntity.ok(trainingSheetService.salvar(trainingSheetDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar", description = "Editar uma ficha de treino.")
    public ResponseEntity<TrainingSheetDTO> editar(
            @PathVariable Integer id,
            @RequestBody TrainingSheetDTO trainingSheetDTO
    ){
        return ResponseEntity.ok(trainingSheetService.editar(id, trainingSheetDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar", description = "Deletar uma ficha de treino.")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id){
        trainingSheetService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
