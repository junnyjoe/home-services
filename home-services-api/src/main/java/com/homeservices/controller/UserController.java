package com.homeservices.controller;

import com.homeservices.dto.UserDTO;
import com.homeservices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur utilisateur
 * Gère les profils et la liste des utilisateurs
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * GET /api/users/me
     * Récupère le profil de l'utilisateur connecté
     */
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    /**
     * PUT /api/users/me
     * Met à jour le profil de l'utilisateur connecté
     */
    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO request) {
        return ResponseEntity.ok(userService.updateProfile(request));
    }

    /**
     * GET /api/users/providers
     * Liste tous les prestataires (pour les clients)
     */
    @GetMapping("/providers")
    public ResponseEntity<List<UserDTO>> getAllProviders() {
        return ResponseEntity.ok(userService.getAllProviders());
    }

    /**
     * GET /api/users/{id}
     * Récupère un utilisateur par ID (admin uniquement)
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRATEUR')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * GET /api/users
     * Liste tous les utilisateurs (admin uniquement)
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRATEUR')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * GET /api/users/search
     * Recherche des utilisateurs par nom
     */
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMINISTRATEUR')")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String name) {
        return ResponseEntity.ok(userService.searchUsers(name));
    }
}
