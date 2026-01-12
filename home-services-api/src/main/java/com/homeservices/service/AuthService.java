package com.homeservices.service;

import com.homeservices.config.JwtUtils;
import com.homeservices.dto.*;
import com.homeservices.entity.ProviderService;
import com.homeservices.entity.ServiceCatalog;
import com.homeservices.entity.User;
import com.homeservices.enums.ServiceType;
import com.homeservices.enums.UserRole;
import com.homeservices.repository.ProviderServiceRepository;
import com.homeservices.repository.ServiceCatalogRepository;
import com.homeservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service d'authentification
 * Gère l'inscription, la connexion et la génération de tokens JWT
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceCatalogRepository serviceCatalogRepository;

    @Autowired
    private ProviderServiceRepository providerServiceRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Inscription d'un nouvel utilisateur
     * - Vérifie si l'email existe déjà
     * - Hash le mot de passe
     * - Crée le service prestataire si applicable
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Vérifier si l'email existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // Créer l'utilisateur
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(request.getRole());

        user = userRepository.save(user);

        // Si c'est un prestataire, créer son service
        if (request.getRole() == UserRole.PRESTATAIRE && request.getServiceType() != null) {
            ServiceCatalog catalog = serviceCatalogRepository.findByType(request.getServiceType())
                    .orElseThrow(() -> new RuntimeException("Type de service non trouvé"));

            ProviderService providerService = new ProviderService();
            providerService.setProvider(user);
            providerService.setServiceCatalog(catalog);
            providerService.setPrice(request.getPrice());
            providerService.setDescription(request.getServiceDescription());
            providerService.setExperienceYears(request.getExperienceYears());
            providerService.setAvailable(true);

            providerServiceRepository.save(providerService);
        }

        // Générer les tokens
        String token = jwtUtils.generateToken(user.getEmail());
        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());

        return new AuthResponse(token, refreshToken, jwtUtils.getExpirationMs(), new UserDTO(user));
    }

    /**
     * Connexion d'un utilisateur
     * - Authentifie avec email/password
     * - Génère un nouveau token JWT
     */
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String token = jwtUtils.generateToken(user.getEmail());
        String refreshToken = jwtUtils.generateRefreshToken(user.getEmail());

        return new AuthResponse(token, refreshToken, jwtUtils.getExpirationMs(), new UserDTO(user));
    }

    /**
     * Rafraîchit le token JWT
     */
    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh token invalide");
        }

        String email = jwtUtils.getEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String newToken = jwtUtils.generateToken(email);
        String newRefreshToken = jwtUtils.generateRefreshToken(email);

        return new AuthResponse(newToken, newRefreshToken, jwtUtils.getExpirationMs(), new UserDTO(user));
    }

    /**
     * Récupère l'utilisateur actuellement connecté
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}
