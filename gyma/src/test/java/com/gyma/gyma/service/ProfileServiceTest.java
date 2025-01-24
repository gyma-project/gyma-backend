package com.gyma.gyma.service;

import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.controller.specificiations.ProfileSpecification;
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
                "username", "email", "firstName", "lastName", UUID.randomUUID(), 0, 10
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
        when(profileMapper.toDTO(profile)).thenReturn(mock(ProfileRequestDTO.class));

        ProfileRequestDTO result = profileService.buscarPorId(1);

        assertNotNull(result);
        verify(profileRepository, times(1)).findById(1);
        verify(profileMapper, times(1)).toDTO(profile);
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
        ProfileRequestDTO dto = mock(ProfileRequestDTO.class);
        when(dto.username()).thenReturn("username");
        when(dto.email()).thenReturn("email@example.com");
        when(dto.firstName()).thenReturn("First");
        when(dto.lastName()).thenReturn("Last");
        when(dto.keycloakUserId()).thenReturn(UUID.randomUUID());

        Profile profile = new Profile();
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);
        when(profileMapper.toDTO(profile)).thenReturn(dto);

        ProfileRequestDTO result = profileService.criar(dto);

        assertNotNull(result);
        verify(profileRepository, times(1)).save(any(Profile.class));
        verify(profileMapper, times(1)).toDTO(profile);
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

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
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
