package com.gyma.gyma.config;

import com.gyma.gyma.config.JWTConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/trainers/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new JWTConverter()) // Conversão de roles
                        )
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Verifique se o URI está correto
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:8080/realms/gyma/protocol/openid-connect/certs").build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(
                  "/v2/api-docs/**",
                  "/v3/api-docs/**",
                  "/swagger-resources/**",
                  "/swagger-ui.html/**",
                  "/swagger-ui/**",
                  "/webjars/**"
        );
    }

}
