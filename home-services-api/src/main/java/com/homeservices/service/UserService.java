package com.homeservices.service;

import com.homeservices.dto.UserDTO;
import com.homeservices.entity.User;
import com.homeservices.enums.UserRole;
import com.homeservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des utilisateurs
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Récupère l'utilisateur actuellement connecté
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    /**
     * Récupère le profil de l'utilisateur courant
     */
    public UserDTO getCurrentUserProfile() {
        return new UserDTO(getCurrentUser());
    }

    /**
     * Met à jour le profil utilisateur
     */
    @Transactional
    public UserDTO updateProfile(UserDTO updateRequest) {
        User user = getCurrentUser();

        if (updateRequest.getFirstName() != null) {
            user.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getLastName() != null) {
            user.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getPhone() != null) {
            user.setPhone(updateRequest.getPhone());
        }
        if (updateRequest.getAddress() != null) {
            user.setAddress(updateRequest.getAddress());
        }

        user = userRepository.save(user);
        return new UserDTO(user);
    }

    /**
     * Liste tous les prestataires
     */
    public List<UserDTO> getAllProviders() {
        return userRepository.findByRole(UserRole.PRESTATAIRE)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un utilisateur par ID (admin)
     */
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return new UserDTO(user);
    }

    /**
     * Liste tous les utilisateurs (admin)
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Compte les utilisateurs par rôle
     */
    public Long countByRole(UserRole role) {
        return userRepository.countByRole(role);
    }

    /**
     * Recherche des utilisateurs
     */
    public List<UserDTO> searchUsers(String name) {
        return userRepository.searchByName(name)
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }
}
