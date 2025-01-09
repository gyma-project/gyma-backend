package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.service.TrainerService;
import com.gyma.gyma.service.TrainingTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("training-times")
public class TrainingTimeController {

    @Autowired
    private TrainingTimeService trainingTimeService;

    @Autowired
    private TrainerService trainerService;

    // @GetMapping
    //public ResponseEntity<List<TrainingTime>> getAllTrainingTimes() {
        //List<TrainingTime> trainingTimes = trainingTimeService.getAllTrainingTimes();
        //return ResponseEntity.ok(trainingTimes);
    //}
    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @PostMapping
    public ResponseEntity<TrainingTime> salvar(@RequestBody TrainingTimeDTO training){
        trainingTimeService.salvar(training);
        return new ResponseEntity("Horário de Treino salvo com sucesso!", HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER')")
    @PatchMapping("/{id}")
    public ResponseEntity<TrainingTime> editarParcialmente(
            @PathVariable Integer id,
            @RequestBody TrainingTimeDTO trainingTimeDTO
    ){
        TrainingTime trainingTime = trainingTimeService.editar(id, trainingTimeDTO);
        return ResponseEntity.ok(trainingTime);
    }

}
