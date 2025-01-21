package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.controller.specificiations.TrainingTimeSpecification;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.ProfileMapper;
import com.gyma.gyma.mappers.TrainingTimeMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.repository.DayRepository;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    public Page<TrainingTime> listarTodos(
            LocalTime startTime,
            LocalTime endTime,
            Integer studentLimit,
            String dayName,
            UUID keycloakUserID,
            Boolean active,
            UUID updateBy,
            Integer pageNumber,
            Integer size
    ) {
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (size == null) {
            size = 10;
        }

        Pageable page = PageRequest.of(pageNumber, size);

        Specification<TrainingTime> spec = Specification.where(
                TrainingTimeSpecification.byStartTime(startTime))
                .and(TrainingTimeSpecification.byEndTime(endTime))
                .and(TrainingTimeSpecification.byDayName(dayName))
                .and(TrainingTimeSpecification.byActiveStatus(active))
                .and(TrainingTimeSpecification.byTrainerKeycloakId(keycloakUserID))
                .and(TrainingTimeSpecification.byStudentLimit(studentLimit)
        );

        return trainingTimeRepository.findAll(spec, page);
    }

    public TrainingTimeDTO buscarPorId(Integer id){
        TrainingTime trainingTime = trainingTimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horário de treino não encontrado."));
        return trainingTimeMapper.toDTO(trainingTime);
    }

    public TrainingTime editar(Integer id, TrainingTimeDTO trainingTimeDTO){
        TrainingTime trainingTime = trainingTimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Horário de treino não encontrado."));

        Profile trainer = profileRepository.findByKeycloakId(trainingTimeDTO.trainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Treinador não encontrado."));

        Profile updateUser = profileRepository.findByKeycloakId(trainingTimeDTO.updateBy())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário de atualização não encontrado."));


        trainingTimeMapper.updateEntityFromDTO(trainingTimeDTO, trainingTime);

        trainingTime.setTrainer(trainer);
        trainingTime.setUpdateBy(updateUser);

        return trainingTimeRepository.save(trainingTime);
    }

}