package com.homeservices.repository;

import com.homeservices.entity.User;
import com.homeservices.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité User
 * Fournit des méthodes d'accès à la base de données pour les utilisateurs
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son email
     */
    Optional<User> findByEmail(String email);

    /**
     * Vérifie si un email existe déjà
     */
    Boolean existsByEmail(String email);

    /**
     * Liste tous les utilisateurs d'un rôle donné
     */
    List<User> findByRole(UserRole role);

    /**
     * Compte le nombre d'utilisateurs par rôle
     */
    Long countByRole(UserRole role);

    /**
     * Recherche des utilisateurs par nom ou prénom
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> searchByName(String name);

    /**
     * Liste les prestataires ayant un service spécifique
     */
    @Query("SELECT DISTINCT u FROM User u JOIN u.providerServices ps " +
            "WHERE ps.serviceCatalog.type = :serviceType AND ps.available = true")
    List<User> findProvidersByServiceType(String serviceType);
}
