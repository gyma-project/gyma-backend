package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainerDTO;
import com.gyma.gyma.model.Trainer;
import com.gyma.gyma.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRep;

    public Trainer buscarPorId(Integer id) {
        return trainerRep.findById(id).orElse(null);
    }

    public Trainer salvar(Trainer trainer){
        return trainerRep.save(trainer);
    }

}
