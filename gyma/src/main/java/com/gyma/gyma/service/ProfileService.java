package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.specificiations.ProfileSpecification;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.ProfileMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.model.Role;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.repository.RoleRepository;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MinioClient minioClient;


    public Page<Profile> listarTodos(
            String username,
            String email,
            String firstName,
            String lastName,
            UUID keycloakID,
            String role,
            Integer pageNumber,
            Integer size
    ) {
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (size == null) {
            size = 10;
        }

        Pageable page = PageRequest.of(pageNumber, size);

        Specification<Profile> spec = Specification.where(
                ProfileSpecification.byUsername(username)
                .and(ProfileSpecification.byEmail(email))
                .and(ProfileSpecification.byKeycloakId(keycloakID))
                .and(ProfileSpecification.byFirstName(firstName))
                .and(ProfileSpecification.byLastName(lastName))
                .and(ProfileSpecification.byRole(role))
        );

        return profileRepository.findAll(spec, page);
    }

    public Profile buscarPorId(Integer id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado para o ID: " + id));

        return profile;
    }

    public ProfileRequestDTO buscarPorUUID(UUID uuid){
        Profile profile = profileRepository.findByKeycloakId(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado para o ID: " + uuid));
        return profileMapper.toDTO(profile);
    }

    public ProfileRequestDTO criar(ProfileRequestDTO profileRequestDTO) {
        profileExists(profileRequestDTO);
        Profile profile = new Profile();
        profile.setUsername(profileRequestDTO.username());
        profile.setEmail(profileRequestDTO.email());
        profile.setFirstName(profileRequestDTO.firstName());
        profile.setLastName(profileRequestDTO.lastName());
        profile.setKeycloakId(profileRequestDTO.keycloakUserId());
        profile.setActive(true);
        profile.setImageUrl(profile.getImageUrl());

        if (profileRequestDTO.roleIds() != null && !profileRequestDTO.roleIds().isEmpty()) {
            Set<Integer> roleIdsSet = new HashSet<>(profileRequestDTO.roleIds());
            List<Role> rolesList = roleRepository.findAllById(roleIdsSet);
            Set<Role> roles = new HashSet<>(rolesList);
            profile.setRoles(roles);
        }

        profileRepository.save(profile);
        return profileMapper.toDTO(profile);
    }

    public Profile atualizar(Integer id, ProfileRequestDTO profileRequestDTO) {
        Profile profile = buscarPorId(id);
        profile.setUsername(profileRequestDTO.username());
        profile.setEmail(profileRequestDTO.email());
        profile.setKeycloakId(profileRequestDTO.keycloakUserId());
        profile.setImageUrl(profile.getImageUrl());
        return profileRepository.save(profile);
    }

    public void deletar(Integer id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Perfil não encontrado para o ID: " + id
                ));
        profileRepository.delete(profile);
    }

    // Métodos de validações
    private void profileExists(ProfileRequestDTO profileRequestDTO){
        if (profileRepository.existsByUsername(profileRequestDTO.username())) {
            throw new IllegalArgumentException("Já existe um perfil com o username informado.");
        }
        if (profileRepository.existsByEmail(profileRequestDTO.email())) {
            throw new IllegalArgumentException("Já existe um perfil com o email informado.");
        }
        if (profileRepository.existsByKeycloakId(profileRequestDTO.keycloakUserId())) {
            throw new IllegalArgumentException("Já existe um perfil com o ID do Keycloak informado.");
        }
    }

    public Set<Role> getRolesByIds(Set<Integer> roleIds) {
        return roleRepository.findAllById(roleIds).stream()
                .collect(Collectors.toSet());
    }

    public Profile toggleActive(Integer id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado para o ID: " + id));

        if(!profile.getActive()){
            profile.setActive(true);
        } else {
            profile.setActive(false);
        }
        return profileRepository.save(profile);
    }

}
