package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/profiles")
@Tag(name = "Perfis", description = "Gerenciamento de perfis.")
public class ProfileController {

    @Autowired
    private ProfileService profileService;


    @GetMapping
    @Operation(summary = "Listar", description = "Listar todos os perfis.")
    public Page<Profile> listarTodos(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) UUID keycloakId,
            @RequestParam(required = false) String roles,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer size
    ) {
        return profileService.listarTodos(username, email, firstName, lastName, keycloakId, roles, pageNumber, size);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar", description = "Buscar um perfil.")
    public ResponseEntity<Profile> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(profileService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar", description = "Criar um perfil.")
    public ResponseEntity<ProfileRequestDTO> criar(
            @RequestBody ProfileRequestDTO profileRequestDTO
    ) {
        return ResponseEntity.ok(profileService.criar(profileRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> atualizar(
            @PathVariable Integer id,
            @RequestBody ProfileRequestDTO profileRequestDTO
    ) {
        return ResponseEntity.ok(profileService.atualizar(id, profileRequestDTO));
    }

    @PostMapping("/{id}/toggle-active")
    @Operation(summary = "Alternar status ativo", description = "Alterna o status ativo de um perfil.")
    public ResponseEntity<Profile> toggleActive(@PathVariable Integer id){
        return ResponseEntity.ok(profileService.toggleActive(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar", description = "Deletar um perfil.")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        profileService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
