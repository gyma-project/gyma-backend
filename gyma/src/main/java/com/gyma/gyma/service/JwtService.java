package com.gyma.gyma.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getSubject();
        } else {
            throw new IllegalStateException("Usuário não autenticado");
        }
    }

    public Collection<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");

            List<String> roles = null;

            if (realmAccess != null && realmAccess.containsKey("roles")) {
                roles = (List<String>) realmAccess.get("roles");
            }

            if (roles != null) {
                return roles.stream()
                        .map(role -> role.replace("ROLE_", ""))
                        .collect(Collectors.toList());
            }

        }

        throw new IllegalStateException("Não foi possível obter as roles do usuário");
    }
}
