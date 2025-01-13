package com.gyma.gyma.mappers;


import com.gyma.gyma.controller.dto.ExerciseDTO;
import com.gyma.gyma.model.Exercise;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    ExerciseDTO toDTO(Exercise exercise);
    Exercise toEntity(Exercise exercise);
}
