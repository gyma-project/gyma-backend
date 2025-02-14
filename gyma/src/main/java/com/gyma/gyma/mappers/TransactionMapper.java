package com.gyma.gyma.mappers;

import com.gyma.gyma.controller.dto.TransactionDTO;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = ProfileMapper.class)
public interface TransactionMapper {

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

    @Mapping(source = "createdBy.keycloakId", target = "createdById")
    @Mapping(source = "updateBy.keycloakId", target = "updateById")
    TransactionDTO toDTO(Transaction transaction);

    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "updateById", target = "updateBy")
    Transaction toEntity(TransactionDTO dto);

    void updateEntityFromDTO(
            TransactionDTO transactionDTO,
            @MappingTarget Transaction transaction
    );
}
