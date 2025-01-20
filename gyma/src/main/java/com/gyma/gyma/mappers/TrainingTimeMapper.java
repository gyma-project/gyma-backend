package com.gyma.gyma.mappers;

import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.model.TrainingTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface TrainingTimeMapper {
    TrainingTimeMapper INSTANCE = Mappers.getMapper(TrainingTimeMapper.class);

    @Mapping(source = "trainer.keycloakId", target = "trainerId")
    TrainingTimeDTO toDTO(TrainingTime trainingTime);

    @Mapping(source = "trainerId", target = "trainer.keycloakId")
    TrainingTime toEntity(TrainingTimeDTO trainingTimeDTO);

    void updateEntityFromDTO(
            TrainingTimeDTO trainingTimeDTO,
            @MappingTarget TrainingTime trainingTime
    );
}
