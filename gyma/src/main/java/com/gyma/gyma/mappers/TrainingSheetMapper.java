package com.gyma.gyma.mappers;

import com.gyma.gyma.controller.dto.TrainingSheetDTO;
import com.gyma.gyma.model.TrainingSheet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingSheetMapper {
    TrainingSheetDTO toDTO(TrainingSheet trainingSheet);
    TrainingSheet toEntity(TrainingSheetDTO trainingSheetDTO);
}
