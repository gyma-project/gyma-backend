package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.TrainerDTO;
import com.gyma.gyma.model.Trainer;
import com.gyma.gyma.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("trainers")
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @PostMapping
    public ResponseEntity<Trainer> salvar(@RequestBody TrainerDTO trainerDTO){
        // Criar validações (CPF duplicado, Email duplicado)
        Trainer trainer = new Trainer();
        trainer.setName(trainerDTO.nomeTreinador());
        trainer.setEmail(trainerDTO.emailTreinador());
        trainer.setCpfTreinador(trainerDTO.cpfTreinador());

        Trainer saveTrainer = trainerService.salvar(trainer);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveTrainer);
    }

    @GetMapping
    public ResponseEntity<List<Trainer>> listarTreinadores(@RequestBody TrainerDTO trainerDTO){
        List<Trainer> trainers = trainerService.buscarTodos();
        if(trainers.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trainers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trainer> buscarTreinador(@PathVariable Integer id){
        Trainer trainer = trainerService.buscarPorId(id);
        if(trainer == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(trainer);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Trainer> editarTreinador(
            @PathVariable Integer id,
            @RequestBody TrainerDTO trainerDTO
    ){
        Trainer trainer = trainerService.editar(id, trainerDTO);
        return ResponseEntity.ok(trainer);
    }
}
