package com.gyma.gyma.mappers;

import com.gyma.gyma.controller.dto.TrainingSheetDTO;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingSheet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface TrainingSheetMapper {

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

    @Mapping(source = "student.keycloakId", target = "student")
    @Mapping(source = "trainer.keycloakId", target = "trainer")
    @Mapping(source = "updateBy.keycloakId", target = "updateBy")
    TrainingSheetDTO toDTO(TrainingSheet trainingSheet);

    @Mapping(source = "student", target = "student.keycloakId")
    @Mapping(source = "trainer", target = "trainer.keycloakId")
    @Mapping(source = "updateBy", target = "updateBy.keycloakId")
    TrainingSheet toEntity(TrainingSheetDTO trainingSheetDTO);

    @Mapping(source = "student", target = "student.keycloakId")
    @Mapping(source = "trainer", target = "trainer.keycloakId")
    @Mapping(source = "updateBy", target = "updateBy.keycloakId")
    void updateEntityFromDTO(
            TrainingSheetDTO trainingSheetDTO,
            @MappingTarget TrainingSheet trainingSheet
    );
}
