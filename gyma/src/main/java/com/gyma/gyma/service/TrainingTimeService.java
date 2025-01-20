package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.ProfileMapper;
import com.gyma.gyma.mappers.TrainingTimeMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.repository.DayRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingTimeService {

    @Autowired
    private TrainingTimeRepository trainingTimeRepository;

    @Autowired
    private TrainingTimeMapper trainingTimeMapper;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileMapper profileMapper;

    public List<TrainingTime> listarTodos() {
        return trainingTimeRepository.findAll();
    }

    public TrainingTimeDTO buscarPorId(Integer id){
        TrainingTime trainingTime = trainingTimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horário de treino não encontrado."));
        return trainingTimeMapper.toDTO(trainingTime);
    }

    public TrainingTime editar(Integer id, TrainingTimeDTO trainingTimeDTO){
        TrainingTime trainingTime = trainingTimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horário de treino não encontrado."));

        ProfileRequestDTO trainer_exists = profileService.buscarPorUUID(trainingTimeDTO.trainerId());

        trainingTimeMapper.updateEntityFromDTO(trainingTimeDTO, trainingTime);

        Profile trainer = profileMapper.toEntity(trainer_exists);

        trainingTime.setTrainer(trainer);

        return trainingTimeRepository.save(trainingTime);
    }

}