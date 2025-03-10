package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.exception.ResourceNotFoundException;
import com.gyma.gyma.mappers.ProfileMapper;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

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

    @Test
    void testListarTodos() {
        Page<Profile> page = new PageImpl<>(Collections.singletonList(new Profile()));
        when(profileRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        Page<Profile> result = profileService.listarTodos(
                "username",
                "email",
                "firstName",
                "lastName", UUID.randomUUID(),
                "ADMIN",
                0,
                1
        );

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(profileRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
    }

    @Test
    void testBuscarPorId() {
        Profile profile = new Profile();
        profile.setId(1);

        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));

        Profile result = profileService.buscarPorId(1);

        assertNotNull(result, "O resultado não pode ser nulo.");
        assertEquals(profile, result, "O perfil retornado deve ser igual ao mockado.");
        verify(profileRepository, times(1)).findById(1);
        verifyNoInteractions(profileMapper);
    }

    @Test
    void testBuscarPorId_NotFound() {
        when(profileRepository.findById(1)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> profileService.buscarPorId(1)
        );

        assertEquals("Perfil não encontrado para o ID: 1", exception.getMessage());
        verify(profileRepository, times(1)).findById(1);
    }

    @Test
    void testBuscarPorUUID() {
        Profile profile = new Profile();
        profile.setKeycloakId(UUID.randomUUID());
        when(profileRepository.findByKeycloakId(profile.getKeycloakId())).thenReturn(Optional.of(profile));
        when(profileMapper.toDTO(profile)).thenReturn(mock(ProfileRequestDTO.class));

        ProfileRequestDTO result = profileService.buscarPorUUID(profile.getKeycloakId());

        assertNotNull(result);
        verify(profileRepository, times(1)).findByKeycloakId(profile.getKeycloakId());
        verify(profileMapper, times(1)).toDTO(profile);
    }

    @Test
    void testCriar() {
        UUID keycloakId = UUID.randomUUID();
        ProfileRequestDTO dto = mock(ProfileRequestDTO.class);
        when(dto.username()).thenReturn("username");
        when(dto.email()).thenReturn("email@example.com");
        when(dto.firstName()).thenReturn("First");
        when(dto.lastName()).thenReturn("Last");
        when(dto.keycloakUserId()).thenReturn(keycloakId);

        Profile profile = new Profile();
        profile.setUsername("username");
        profile.setEmail("email@example.com");
        profile.setFirstName("First");
        profile.setLastName("Last");
        profile.setKeycloakId(keycloakId);

        // Configuração dos retornos dos mocks
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(profileMapper.toDTO(any(Profile.class))).thenReturn(dto); // Ajuste aqui

        ProfileRequestDTO result = profileService.criar(dto);

        assertNotNull(result);
        assertEquals("username", result.username());
        assertEquals("email@example.com", result.email());
        verify(profileRepository, times(1)).save(any(Profile.class));
        verify(profileMapper, times(1)).toDTO(any(Profile.class));
    }

    @Test
    void testCriar_UsernameAlreadyExists() {
        ProfileRequestDTO dto = mock(ProfileRequestDTO.class);
        when(dto.username()).thenReturn("existingUsername");
        when(profileRepository.existsByUsername("existingUsername")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> profileService.criar(dto)
        );

        assertEquals("Já existe um perfil com o username informado.", exception.getMessage());
    }

    @Test
    void testCriar_EmailAlreadyExists() {
        ProfileRequestDTO dto = mock(ProfileRequestDTO.class);
        when(dto.email()).thenReturn("existingEmail@example.com");
        when(profileRepository.existsByEmail("existingEmail@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> profileService.criar(dto)
        );

        assertEquals("Já existe um perfil com o email informado.", exception.getMessage());
    }

    @Test
    void testDeletar() {
        Profile profile = new Profile();
        profile.setId(1);
        when(profileRepository.findById(1)).thenReturn(Optional.of(profile));

        profileService.deletar(1);

        verify(profileRepository, times(1)).findById(1);
        verify(profileRepository, times(1)).delete(profile);
    }

    @Test
    void testDeletar_NotFound() {
        when(profileRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> profileService.deletar(1)
        );

        assertEquals("Perfil não encontrado para o ID: 1", exception.getMessage());
        verify(profileRepository, times(1)).findById(1);
    }

    @Test
    void testProfileExists_UsernameAlreadyExists() {
        ProfileRequestDTO dto = mock(ProfileRequestDTO.class);
        when(dto.username()).thenReturn("existingUsername");
        when(profileRepository.existsByUsername("existingUsername")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> profileService.criar(dto)
        );

        assertEquals("Já existe um perfil com o username informado.", exception.getMessage());
    }

    @Test
    void testProfileExists_EmailAlreadyExists() {
        ProfileRequestDTO dto = mock(ProfileRequestDTO.class);
        when(dto.email()).thenReturn("existingEmail@example.com");
        when(profileRepository.existsByEmail("existingEmail@example.com")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> profileService.criar(dto)
        );

        assertEquals("Já existe um perfil com o email informado.", exception.getMessage());
    }
}

