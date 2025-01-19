package com.gyma.gyma.mappers;

import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.model.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileRequestDTO toDTO(Profile profile);
    Profile toEntity(ProfileRequestDTO profileRequestDTO);

}
