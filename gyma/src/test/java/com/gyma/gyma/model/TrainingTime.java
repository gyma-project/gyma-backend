package com.gyma.gyma.model;


import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.controller.dto.TrainingTimeDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class TrainingTime {

    @Autowired
    EntityManager entityManager;

    @Test
    void findByKeycloakId(){

    }


    private TrainingTime createTrainingTime(TrainingTimeDTO data){

    }

    private Profile createProfile(ProfileRequestDTO data){

    }

}
