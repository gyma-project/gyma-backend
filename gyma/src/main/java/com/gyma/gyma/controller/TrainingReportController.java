package com.gyma.gyma.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/training-reports")
@Tag(name = "Relatórios de Treino", description = "Gerenciamento de relatórios de treino.")
public class TrainingReportController {

    //@Autowired
    //private TrainingReportService trainingReportService;

}
