package com.gyma.gyma.mappers;

import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.controller.dto.TrainingSheetDTO;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.model.TrainingSheet;
import com.gyma.gyma.model.TrainingTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface TrainingRecordMapper {

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
    TrainingRecordDTO toDTO(TrainingRecord trainingRecord);

    @Mapping(source = "student", target = "student.keycloakId")
    @Mapping(source = "trainer", target = "trainer.keycloakId")
    TrainingRecord toEntity(TrainingRecordDTO trainingRecordDTO);

    @Mapping(source = "student", target = "student.keycloakId")
    @Mapping(source = "trainer", target = "trainer.keycloakId")
    void updateEntityFromDTO(
            TrainingSheetDTO trainingSheetDTO,
            @MappingTarget TrainingSheet trainingSheet
    );
}
