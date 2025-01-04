package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainerDTO;
import com.gyma.gyma.model.Trainer;
import com.gyma.gyma.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRep;

    public Trainer buscarPorId(Integer id) {
        return trainerRep.findById(id).orElse(null);
    }

    public Trainer salvar(Trainer trainer){
        // Validação de CPF duplicado
        if (trainerRep.existsByCpfTreinador(trainer.getCpfTreinador())) {
            throw new RuntimeException("CPF já cadastrado!");
        }

        // Validação de e-mail duplicado
        if (trainerRep.existsByEmail(trainer.getEmail())) {
            throw new RuntimeException("E-mail já cadastrado!");
        }
        return trainerRep.save(trainer);
    }

    public List<Trainer> buscarTodos(){
        return trainerRep.findAll();
    }

    public Trainer editar(Integer id, TrainerDTO trainerDTO){
        Optional<Trainer> trainerOptional = trainerRep.findById(id);

        if (trainerOptional.isPresent()){
            Trainer trainer = trainerOptional.get();
            trainer.setName(trainerDTO.nomeTreinador());
            trainer.setEmail(trainerDTO.emailTreinador());
            trainer.setCpfTreinador(trainerDTO.cpfTreinador());

            return trainerRep.save(trainer);
        } else {
            throw new RuntimeException("Treinador não encontrado para o id: " + id);
        }

    }

}
