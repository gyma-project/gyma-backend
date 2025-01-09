package com.gyma.gyma.mappers;

import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import com.gyma.gyma.model.TrainingTime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrainingTimeMapper {
    TrainingTimeMapper INSTANCE = Mappers.getMapper(TrainingTimeMapper.class);
    TrainingTimeDTO toDTO(TrainingTime trainingTime);
    TrainingTime toEntity(TrainingTimeDTO trainingTimeDTO);
    void updateEntityFromDTO(TrainingTimeDTO trainingTimeDTO, @MappingTarget TrainingTime trainingTime);
}
