package com.gyma.gyma.service;

import com.gyma.gyma.model.Day;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.model.enums.DayOfTheWeek;
import com.gyma.gyma.repository.DayRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.UUID;

@Service
public class DayService {

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private TrainingTimeRepository trainingTimeRepository;

    // Método para criar um Dia com os horários automáticos
    public Day createDayWithTrainingTimes(DayOfTheWeek dayOfTheWeek) {
        // Criar e salvar o Dia
        Day day = new Day();
        day.setName(dayOfTheWeek);
        day = dayRepository.save(day);  // Salva o Dia no banco

        // Criar os horários de 5h até 23h
        for (int hour = 5; hour <= 23; hour++) {
            LocalTime startTime = LocalTime.of(hour, 0);
            LocalTime endTime = LocalTime.of(hour + 1, 0);

            // Criar e salvar o horário
            TrainingTime trainingTime = new TrainingTime();
            trainingTime.setStartTime(startTime);
            trainingTime.setEndTime(endTime);
            trainingTime.setStudentsLimit(30);  // Exemplo de limite de alunos
            trainingTime.setActive(true);       // Horário ativo
            trainingTime.setTrainerId(UUID.randomUUID());  // Exemplo de ID do treinador
            trainingTime.setIdUsuario(UUID.randomUUID());  // Exemplo de ID do usuário que editou

            trainingTime.getDay().add(day);

            trainingTimeRepository.save(trainingTime);  // Salva o horário
        }

        return day;  // Retorna o Dia criado
    }
}

