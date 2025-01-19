package com.gyma.gyma.controller.dto;

import java.util.UUID;

public record ProfileRequestDTO(
        String username,
        String email,
        String firstName,
        String lastName,
        UUID keycloakUserId
) {

}
