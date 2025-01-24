package com.gyma.gyma.controller.specificiations;


import com.gyma.gyma.model.Profile;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

public class ProfileSpecification {

    public static Specification<Profile> byUsername(String username){
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(username)){
                return null;
            }
            return builder.like(root.get("username"), "%" + username + "%");
        };
    }

    public static Specification<Profile> byEmail(String email){
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(email)){
                return null;
            }
            return builder.like(root.get("email"), "%" + email + "%");
        };
    }

    public static Specification<Profile> byFirstName(String firstName){
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(firstName)){
                return null;
            }
            return builder.like(root.get("firstName"), "%" + firstName.toLowerCase() + "%");
        };
    }

    public static Specification<Profile> byLastName(String lastName){
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(lastName)){
                return null;
            }
            return builder.like(root.get("lastName"), "%" + lastName.toLowerCase() + "%");
        };
    }

    public static Specification<Profile> byKeycloakId(UUID keycloakId){
        return (root, query, builder) -> {
            if(ObjectUtils.isEmpty(keycloakId)){
                return null;
            }
            return builder.equal(root.get("keycloakId"), keycloakId);
        };
    }

    public static Specification<Profile> byRole(String roleName) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(roleName)) {
                return null;
            }
            // Faz um join na tabela de roles e filtra pelo nome
            return builder.equal(
                    root.join("roles").get("name"), roleName
            );
        };
    }

}
