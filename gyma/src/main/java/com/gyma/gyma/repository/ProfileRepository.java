package com.gyma.gyma.repository;

import com.gyma.gyma.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, Integer>, JpaSpecificationExecutor<Profile>{

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByKeycloakId(UUID keycloakId);

    @Override
    Optional<Profile> findById(Integer integer);
    Optional<Profile> findByUsername(String username);
    Optional<Profile> findByEmail(String email);
    Optional<Profile> findByKeycloakId(UUID keycloakId);
    Optional<Profile> findFirstByOrderByIdAsc();

    List<Profile> findByRoles_Name(String roleName);
}
