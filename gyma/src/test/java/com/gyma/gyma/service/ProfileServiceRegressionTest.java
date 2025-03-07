package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.repository.ProfileRepository;
import com.gyma.gyma.mappers.ProfileMapper;
import com.gyma.gyma.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProfileServiceRegressionTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private ProfileMapper profileMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void testCriarProfile_Regression() {
        // Criação de uma instância de ProfileRequestDTO com os parâmetros corretos
        ProfileRequestDTO profileRequestDTO = new ProfileRequestDTO(
                "johndoe",
                "john@example.com",
                "John",
                "Doe",
                UUID.randomUUID(),
                new HashSet<>() // Novo argumento: Set<Integer>
        );

        // Mock da entidade Profile
        Profile profile = new Profile();
        profile.setUsername("johndoe");
        profile.setEmail("john@example.com");
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setKeycloakId(UUID.randomUUID());
        profile.setActive(true);

        // Mock do repositório
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        // Execução do método
        ProfileRequestDTO result = profileService.criar(profileRequestDTO);

        // Verificações
        assertNotNull(result);
        assertEquals("johndoe", result.username());
        assertEquals("john@example.com", result.email());
        assertEquals("John", result.firstName());
        assertEquals("Doe", result.lastName());

        verify(profileRepository, times(1)).save(any(Profile.class));
    }*/

    @Test
    void testListarTodos_Regression() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Profile> profilesPage = mock(Page.class);

        // Mock do comportamento do repositório
        when(profileRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(profilesPage);

        // Execução do método
        Page<Profile> result = profileService.listarTodos("johndoe", "john@example.com", null, null, null, null, 0, 10);

        // Verificações
        assertNotNull(result);
        verify(profileRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }

    @Test
    void testDeletarProfile_Regression() {
        Integer id = 1;

        // Mock do perfil existente
        Profile existingProfile = new Profile();
        existingProfile.setId(id);

        // Mock do comportamento do repositório
        when(profileRepository.findById(id)).thenReturn(Optional.of(existingProfile));

        // Execução do método
        profileService.deletar(id);

        // Verificações
        verify(profileRepository, times(1)).delete(any(Profile.class));
    }

    @Test
    void testToggleActive_Regression() {
        Integer id = 1;

        // Mock do perfil existente
        Profile profile = new Profile();
        profile.setId(id);
        profile.setActive(true);

        // Mock do comportamento do repositório
        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        // Execução do método
        Profile result = profileService.toggleActive(id);

        // Verificações
        assertNotNull(result);
        assertFalse(result.getActive()); // Verificar se o status foi alterado

        verify(profileRepository, times(1)).save(any(Profile.class));
    }

    @Test
    void testDeletarProfile_NotFound_Regression() {
        Integer id = 1;

        // Mock do comportamento do repositório
        when(profileRepository.findById(id)).thenReturn(Optional.empty());

        // Execução e verificação da exceção
        assertThrows(ResourceNotFoundException.class, () -> profileService.deletar(id));
    }

    @Test
    void testToggleActive_ProfileNotFound_Regression() {
        Integer id = 1;

        // Mock do comportamento do repositório
        when(profileRepository.findById(id)).thenReturn(Optional.empty());

        // Execução e verificação da exceção
        assertThrows(ResourceNotFoundException.class, () -> profileService.toggleActive(id));
    }
}
