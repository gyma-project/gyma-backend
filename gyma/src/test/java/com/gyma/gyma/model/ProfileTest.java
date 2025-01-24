package com.gyma.gyma.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void testProfileCreation() {

        Profile profile = new Profile();
        UUID uuid = UUID.randomUUID();


        profile.setId(1);
        profile.setUsername("pedro");
        profile.setEmail("pedro.silva@example.com");
        profile.setFirstName("Pedro");
        profile.setLastName("Silva");
        profile.setKeycloakId(uuid);


        assertEquals(1, profile.getId());
        assertEquals("pedro", profile.getUsername()); // Corrigido
        assertEquals("pedro.silva@example.com", profile.getEmail());
        assertEquals("Pedro", profile.getFirstName());
        assertEquals("Silva", profile.getLastName());
        assertEquals(uuid, profile.getKeycloakId());
    }

    @Test
    void testUsernameNotNull() {

        Profile profile = new Profile();


        assertThrows(IllegalArgumentException.class, () -> profile.setUsername(null),
                "O username não pode ser nulo.");
    }

    @Test
    void testEmailUniquenessShouldBeHandledInRepository() {

        Profile profile1 = new Profile();
        Profile profile2 = new Profile();

        profile1.setEmail("unique@example.com");
        profile2.setEmail("unique@example.com");


        assertEquals(profile1.getEmail(), profile2.getEmail(), "Os emails podem ser iguais entre instâncias.");
    }
}
