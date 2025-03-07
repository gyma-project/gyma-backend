package com.gyma.gyma.controller;

import com.gyma.gyma.controller.dto.ProfileRequestDTO;
import com.gyma.gyma.model.Profile;
import com.gyma.gyma.service.JwtService;
import com.gyma.gyma.service.ProfileService;
import io.minio.MinioClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/profiles")
@Tag(name = "Perfis", description = "Gerenciamento de perfis.")
@Slf4j
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private JwtService jwtService;

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

    //@PreAuthorize("hasRole('ADMIN') or hasRole('TRAINER') or #id.toString() == authentication.principal.claims['sub'].toString()")
    @PutMapping("/{id}")
    public ResponseEntity<Profile> atualizar(
            @PathVariable Integer id,
            @RequestBody ProfileRequestDTO profileRequestDTO,
            @AuthenticationPrincipal Jwt jwt
    ) {
        log.debug("JWT Claims: {}", jwt.getClaims());
        log.debug("Request ID: {}", id);

        String userId = jwt.getSubject();
        List<String> roles = jwt.getClaimAsMap("realm_access") != null
                ? (List<String>) jwt.getClaimAsMap("realm_access").get("roles")
                : List.of();

        Profile profile = profileService.atualizar(id, profileRequestDTO, userId, roles);
        return ResponseEntity.ok(profile);
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
