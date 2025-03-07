package com.gyma.gyma.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Schema(name = "Ficha de treino")
public record TrainingSheetDTO(
        @NotBlank(message = "O nome da ficha de treino não pode estar vazio.")
        @Size(max = 255, message = "O nome da ficha de treino deve ter no máximo 255 caracteres.")
        String name,

        @NotNull(message = "O ID do aluno é obrigatório.")
        UUID student,

        @NotNull(message = "O ID do treinador é obrigatório.")
        UUID trainer,

        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
        String description,

        @NotEmpty(message = "A ficha de treino deve conter pelo menos um exercício.")
        List<Integer> exerciseIds,

        @NotNull(message = "O ID do usuário que está atualizando a ficha é obrigatório.")
        UUID updateBy
) {
}
