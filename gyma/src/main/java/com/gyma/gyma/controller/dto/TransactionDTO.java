package com.gyma.gyma.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "DTO para transações financeiras.")
public record TransactionDTO(
        @Schema(description = "UUID do perfil que enviou a transação", example = "10")
        UUID senderId,

        @NotNull(message = "O ID do criador (createdById) é obrigatório.")
        @Schema(description = "ID do perfil que criou a transação", example = "2")
        UUID createdById,

        @NotNull(message = "O ID do usuário que atualizou (updateById) é obrigatório.")
        @Schema(description = "ID do perfil que fez a última atualização", example = "3")
        UUID updateById,

        @NotNull(message = "O preço é obrigatório.")
        @Schema(description = "Valor da transação", example = "150.00")
        BigDecimal price,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres.")
        @Schema(description = "Descrição opcional da transação", example = "Mensalidade da academia")
        String description
) {}
