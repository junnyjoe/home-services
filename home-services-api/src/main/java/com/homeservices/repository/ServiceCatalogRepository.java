package com.homeservices.repository;

import com.homeservices.entity.ServiceCatalog;
import com.homeservices.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité ServiceCatalog
 * Gère les types de services disponibles
 */
@Repository
public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalog, Long> {

    /**
     * Recherche un service par son type
     */
    Optional<ServiceCatalog> findByType(ServiceType type);

    /**
     * Liste les services par type
     */
    List<ServiceCatalog> findAllByType(ServiceType type);

    /**
     * Vérifie si un service existe par type
     */
    Boolean existsByType(ServiceType type);

    /**
     * Recherche par nom (case-insensitive)
     */
    List<ServiceCatalog> findByNameContainingIgnoreCase(String name);
}
