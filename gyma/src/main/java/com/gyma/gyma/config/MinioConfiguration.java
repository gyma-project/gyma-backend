package com.gyma.gyma.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {
    @Bean
    MinioClient minioClient(){
        return MinioClient.builder()
            .endpoint("http://localhost:9000")
            .credentials("ROOT", "ROOTROOT")
            .build();
    }


}
