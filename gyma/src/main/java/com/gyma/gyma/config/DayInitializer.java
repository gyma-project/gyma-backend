package com.gyma.gyma.config;

import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.Role;
import com.gyma.gyma.model.TrainingTime;
import com.gyma.gyma.model.enums.DayOfTheWeek;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.RoleRepository;
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
    private TrainingTimeRepository trainingTimeRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    @Async
    public void initializeDays() {
        // Verifica se já há horários cadastrados
        if (trainingTimeRepository.count() > 0) {
            System.out.println("Os dias já existem, pulando esta etapa...");
            return;
        }

        Profile defaultTrainer = profileRepository
                .findFirstByOrderByIdAsc()
                .orElseGet(this::createDefaultTrainer);

        createRolesIfNotExist();

        for (DayOfTheWeek dayOfTheWeek : DayOfTheWeek.values()) {
            createTrainingTimesForDay(dayOfTheWeek, defaultTrainer);
        }

        System.out.println("Os dias e horários foram criados!");
    }

    private Profile createDefaultTrainer() {
        Profile profile = new Profile();
        profile.setUsername("default_trainer");
        profile.setFirstName("Treinador");
        profile.setLastName("Padrão");
        profile.setEmail("default@gyma.com");
        profile.setKeycloakId(UUID.randomUUID());
        profile.setActive(true);
        profileRepository.save(profile);
        return profile;
    }

    private void createRolesIfNotExist() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);

            Role trainerRole = new Role();
            trainerRole.setName("TRAINER");
            roleRepository.save(trainerRole);

            Role studentRole = new Role();
            studentRole.setName("STUDENT");
            roleRepository.save(studentRole);

            System.out.println("As roles foram criadas!");
        } else {
            System.out.println("As roles já existem, pulando esta etapa...");
        }
    }

    private void createTrainingTimesForDay(DayOfTheWeek day, Profile defaultTrainer) {
        int startHour = 5;
        int endHour = 23;

        for (int currentHour = startHour; currentHour <= endHour; currentHour++) {
            LocalTime startTime = LocalTime.of(currentHour, 0);
            LocalTime endTime = startTime.plusHours(1);

            TrainingTime trainingTime = new TrainingTime();
            trainingTime.setDay(day);
            trainingTime.setStartTime(startTime);
            trainingTime.setEndTime(endTime);
            trainingTime.setStudentsLimit(20);
            trainingTime.setTrainer(defaultTrainer);
            trainingTime.setActive(true);
            trainingTime.setUpdateBy(defaultTrainer);

            trainingTimeRepository.save(trainingTime);
        }
    }
}
