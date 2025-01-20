package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.TrainingSheetDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.TrainingSheetMapper;
import com.gyma.gyma.model.Exercise;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingSheet;
import com.gyma.gyma.repository.ExerciseRepository;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.TrainingSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingSheetService {

    @Autowired
    private TrainingSheetRepository trainingSheetRepository;

    @Autowired
    private TrainingSheetMapper trainingSheetMapper;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public TrainingSheetDTO salvar(TrainingSheetDTO trainingSheetDTO){

        Optional<Profile> studentProfile = profileRepository.findByKeycloakId(trainingSheetDTO.student());
        Optional<Profile> trainerProfile = profileRepository.findByKeycloakId(trainingSheetDTO.trainer());
        Optional<Profile> updateByProfile = profileRepository.findByKeycloakId(trainingSheetDTO.updateBy());

        if (studentProfile.isEmpty() || trainerProfile.isEmpty() || updateByProfile.isEmpty()) {
            throw new ResourceNotFoundException("Algum perfil não foi encontrado.");
        }

        TrainingSheet trainingSheet = new TrainingSheet();
        trainingSheet.setStudent(studentProfile.get());
        trainingSheet.setDescription(trainingSheetDTO.description());
        trainingSheet.setTrainer(trainerProfile.get());
        trainingSheet.setUpdateBy(updateByProfile.get());

        List<Exercise> exercises = exerciseRepository.findAllById(
                trainingSheetDTO.exerciseIds()
        );
        if (exercises.size() != trainingSheetDTO.exerciseIds().size()) {
            throw new RuntimeException("Alguns IDs de exercícios são inválidos.");
        }
        trainingSheet.setExercises(exercises);
        return trainingSheetMapper.toDTO(trainingSheetRepository.save(trainingSheet));
    }

    public void deletar(Integer id){
        trainingSheetRepository.deleteById(id);
    }

    public List<TrainingSheet> listar(){
        return trainingSheetRepository.findAll();
    }
}
