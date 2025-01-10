package com.gyma.gyma.mappers;

import com.gyma.gyma.controller.dto.TrainingRecordDTO;
import com.gyma.gyma.model.TrainingRecord;
import com.gyma.gyma.model.TrainingTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TrainingRecordMapper {

    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updateAt", source = "updateAt")
    TrainingRecordDTO toDTO(TrainingRecord trainingRecord);
    TrainingRecord toEntity(TrainingRecordDTO trainingRecordDTO);
}
