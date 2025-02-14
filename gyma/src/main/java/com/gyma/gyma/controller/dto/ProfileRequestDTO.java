package com.gyma.gyma.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record ProfileRequestDTO(
        @NotBlank(message = "O username é obrigatório.")
        String username,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email deve ser válido.")
        String email,

        @NotBlank(message = "O primeiro nome é obrigatório.")
        String firstName,

        @NotBlank(message = "O sobrenome é obrigatório.")
        String lastName,

        @NotNull(message = "O ID do Keycloak é obrigatório.")
        UUID keycloakUserId,

        @NotBlank(message="Papéis não podem ser vazio.")
        Set<Integer> roleIds
) {

}
