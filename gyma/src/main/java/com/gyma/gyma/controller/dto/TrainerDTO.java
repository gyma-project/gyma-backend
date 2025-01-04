package com.gyma.gyma.controller.dto;

import com.gyma.gyma.model.Trainer;

public record TrainerDTO(
        String nomeTreinador,
        String emailTreinador,
        String cpf_treinador
) {

    public Trainer mapearParaTrainer(){
        Trainer trainer = new Trainer();
        trainer.setName(this.nomeTreinador);
        trainer.setEmail(this.emailTreinador);
        trainer.setCpfTreinador(this.cpf_treinador);
        return trainer;
    }

}
