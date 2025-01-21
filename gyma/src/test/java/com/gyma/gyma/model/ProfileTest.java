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
        profile.setUsername("john_doe");
        profile.setEmail("john.doe@example.com");
        profile.setFirstName("John");
        profile.setLastName("Doe");
        profile.setKeycloakId(uuid);

        assertEquals(1, profile.getId());
        assertEquals("john_doe", profile.getUsername());
        assertEquals("john.doe@example.com", profile.getEmail());
        assertEquals("John", profile.getFirstName());
        assertEquals("Doe", profile.getLastName());
        assertEquals(uuid, profile.getKeycloakId());
    }
}