package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.TrainingSheetDTO;
import com.gyma.gyma.model.TrainingSheet;
import com.gyma.gyma.service.TrainingSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("training-sheets")
public class TrainingSheetController {

    @Autowired
    private TrainingSheetService trainingSheetService;

    @GetMapping
    public ResponseEntity<List<TrainingSheet>> listar(){
        return ResponseEntity.ok(trainingSheetService.listar());
    }

    @PostMapping
    public ResponseEntity<TrainingSheetDTO> salvar(
            @RequestBody TrainingSheetDTO trainingSheetDTO
    ){
        return ResponseEntity.ok(trainingSheetService.salvar(trainingSheetDTO));
    }
}
