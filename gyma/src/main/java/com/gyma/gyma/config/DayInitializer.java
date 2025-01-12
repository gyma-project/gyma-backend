package com.gyma.gyma.config;

import com.gyma.gyma.model.Day;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.model.enums.DayOfTheWeek;
import com.gyma.gyma.repository.DayRepository;
import com.gyma.gyma.repository.TrainingTimeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.UUID;

@Component
public class DayInitializer {

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private TrainingTimeRepository trainingTimeRepository;

    @PostConstruct
    @Async
    public void initializeDays() {
        // Verifica se os registros já existem no banco
        if (dayRepository.count() == 0) {
            for (DayOfTheWeek dayOfTheWeek : DayOfTheWeek.values()) {
                Day day = new Day();
                day.setName(dayOfTheWeek);
                day.setActive(true);
                dayRepository.save(day); // Salva o dia inicialmente

                createTrainingTimesForDay(day); // Cria os horários de treino para o dia
            }
            System.out.println("Os dias e horários foram criados!");
        } else {
            System.out.println("Os dias já existem, pulando esta etapa...");
        }
    }

    private void createTrainingTimesForDay(Day day) {
        int startHour = 5;
        int endHour = 23;

        for (int currentHour = startHour; currentHour <= endHour; currentHour++) {
            LocalTime startTime = LocalTime.of(currentHour, 0);
            LocalTime endTime = startTime.plusHours(1);

            TrainingTime trainingTime = new TrainingTime();
            trainingTime.setStartTime(startTime);
            trainingTime.setEndTime(endTime);
            trainingTime.setStudentsLimit(20);
            trainingTime.setTrainerId(UUID.randomUUID());
            trainingTime.setActive(true);
            trainingTime.setIdUsuario(UUID.randomUUID());

            day.getTrainingTimes().add(trainingTime);

            trainingTimeRepository.save(trainingTime);
        }
        dayRepository.save(day);
    }
}
