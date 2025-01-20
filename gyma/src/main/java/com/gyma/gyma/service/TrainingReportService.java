package com.gyma.gyma.service;

import com.gyma.gyma.repository.TrainingReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingReportService {

    @Autowired
    private TrainingReportRepository trainingReportRepository;



}
