package com.gyma.gyma.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@TestConfiguration
public class TestJpaConfiguration {

    @Bean
    public JpaMetamodelMappingContext jpaMetamodelMappingContext() {
        // Retorna um mock do JpaMetamodelMappingContext para evitar a inicialização do metamodelo JPA
        return org.mockito.Mockito.mock(JpaMetamodelMappingContext.class);
    }
}