package com.homeservices.repository;

import com.homeservices.entity.ProviderService;
import com.homeservices.enums.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité ProviderService
 * Gère les services proposés par les prestataires
 */
@Repository
public interface ProviderServiceRepository extends JpaRepository<ProviderService, Long> {

    /**
     * Liste les services d'un prestataire
     */
    List<ProviderService> findByProviderId(Long providerId);

    /**
     * Liste les services disponibles d'un prestataire
     */
    List<ProviderService> findByProviderIdAndAvailableTrue(Long providerId);

    /**
     * Recherche par type de service (disponibles uniquement)
     */
    @Query("SELECT ps FROM ProviderService ps WHERE ps.serviceCatalog.type = :serviceType AND ps.available = true")
    List<ProviderService> findByServiceTypeAndAvailable(ServiceType serviceType);

    /**
     * Vérifie si un prestataire propose déjà ce service
     */
    Boolean existsByProviderIdAndServiceCatalogId(Long providerId, Long serviceId);

    /**
     * Recherche un service spécifique d'un prestataire
     */
    Optional<ProviderService> findByProviderIdAndServiceCatalogId(Long providerId, Long serviceId);

    /**
     * Liste tous les services disponibles
     */
    List<ProviderService> findByAvailableTrue();

    /**
     * Liste les services avec prix trié
     */
    @Query("SELECT ps FROM ProviderService ps WHERE ps.available = true ORDER BY ps.price ASC")
    List<ProviderService> findAllAvailableOrderByPriceAsc();

    /**
     * Liste les services par type, triés par prix
     */
    @Query("SELECT ps FROM ProviderService ps WHERE ps.serviceCatalog.type = :serviceType AND ps.available = true ORDER BY ps.price ASC")
    List<ProviderService> findByTypeOrderByPriceAsc(ServiceType serviceType);
}
