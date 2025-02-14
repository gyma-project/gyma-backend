package com.gyma.gyma.controller.dto;

import com.google.common.xml.XmlEscapers;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.model.enums.DayOfTheWeek;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "Horário de Treino")
public record TrainingTimeDTO(
        @NotNull(message = "Limite de alunos não pode ser vazio.")
        @Min(value = 10, message = "O limite de estudantes deve ser no mínimo 10.")
        Integer studentsLimit,

        @NotNull(message = "ID do treinador não pode ser nulo.")
        UUID trainerId,

        @NotNull(message = "ID do usuário não pode ser nulo.")
        UUID updateBy,

        @NotNull(message = "O campo 'active' não pode ser nulo.")
        Boolean active,

        @NotNull(message="O campo 'dayWeek' não pode ser nulo.")
        DayOfTheWeek dayWeek
) {
}
