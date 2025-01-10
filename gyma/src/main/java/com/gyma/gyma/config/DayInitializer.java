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
import org.springframework.transaction.annotation.Transactional;

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
                dayRepository.save(day);

                createTrainingTimesForDay(day);
            }
            System.out.println("Os dias foram criados!");
        } else {
            System.out.println("Os dias já existem, pulando esta etapa...");
        }
    }

    @Transactional
    private void createTrainingTimesForDay(Day day) {
        int startHour = 5;
        int endHour = 22;

        for (int currentHour = startHour; currentHour <= endHour; currentHour++) {
            LocalTime startTime = LocalTime.of(currentHour, 0);
            LocalTime endTime = startTime.plusHours(1);

            if (!trainingTimeRepository.existsByDayAndStartTime(day, startTime)) {
                TrainingTime trainingTime = createTrainingTime(day, startTime, endTime);
                trainingTimeRepository.save(trainingTime);
                day.getTrainingTimes().add(trainingTime);
                dayRepository.save(day);
            }
        }
    }

    private TrainingTime createTrainingTime(Day day, LocalTime startTime, LocalTime endTime) {
        TrainingTime trainingTime = new TrainingTime();
        trainingTime.setStartTime(startTime);
        trainingTime.setEndTime(endTime);
        trainingTime.setStudentsLimit(20);
        trainingTime.setTrainerId(UUID.randomUUID());
        trainingTime.setActive(true);
        trainingTime.setIdUsuario(UUID.randomUUID());

        trainingTime.getDay().add(day);

        return trainingTime;
    }
}
