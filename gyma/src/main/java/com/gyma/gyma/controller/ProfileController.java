package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public Page<Profile> listarTodos(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) UUID keycloakId,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer size
    ) {
        return profileService.listarTodos(username, email, firstName, lastName, keycloakId, pageNumber, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileRequestDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProfileRequestDTO> criar(
            @RequestBody ProfileRequestDTO profileRequestDTO
    ) {
        return ResponseEntity.ok(profileService.criar(profileRequestDTO));
    }

   // @PutMapping("/{id}")
   // public ResponseEntity<ProfileRequestDTO> atualizar(
   //         @PathVariable Integer id,
   //         @Validated @RequestBody ProfileRequestDTO profileRequestDTO
   // ) {
   //     Profile profile = profileService.atualizar(id, profileRequestDTO);
  //      return ResponseEntity.ok(profileService.toDTO(profile));
   // }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        profileService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
