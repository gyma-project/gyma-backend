package com.gyma.gyma.mappers;

import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.controller.dto.TrainingTimeUpdateDTO;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface TrainingTimeMapper {

    default UUID map(Profile profile) {
        return profile != null ? profile.getKeycloakId() : null;
    }

    default Profile map(UUID keycloakId) {
        if (keycloakId == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setKeycloakId(keycloakId);
        return profile;
    }

    TrainingTimeMapper INSTANCE = Mappers.getMapper(TrainingTimeMapper.class);

    @Mapping(source = "trainer.keycloakId", target = "trainerId")
    @Mapping(source = "updateBy.keycloakId", target = "updateBy")
    TrainingTimeDTO toDTO(TrainingTime trainingTime);

    @Mapping(source = "trainerId", target = "trainer.keycloakId")
    @Mapping(source = "updateBy", target = "updateBy.keycloakId")
    TrainingTime toEntity(TrainingTimeDTO trainingTimeDTO);

    void updateEntityFromDTO(
            TrainingTimeUpdateDTO trainingTimeDTO,
            @MappingTarget TrainingTime trainingTime
    );
}
