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

}

