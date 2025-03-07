package com.gyma.gyma.controller;

import com.gyma.gyma.config.DayInitializer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/initial-data")
@Tag(name = "Dados iniciais", description = "Carregar dados iniciais.")
public class InitialDataController {

    @Autowired
    private DayInitializer dayInitializer;

    @GetMapping("/initialize-days")
    @Operation(summary = "Carregar", description = "Carrega todas as configurações iniciais.")
    public String initializeDays() {
        dayInitializer.initializeDays();
        return "Dias e horários inicializados com sucesso!";
    }

}
