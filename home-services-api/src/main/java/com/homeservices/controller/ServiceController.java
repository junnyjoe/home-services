package com.homeservices.controller;

import com.homeservices.dto.ProviderServiceDTO;
import com.homeservices.dto.ServiceCatalogDTO;
import com.homeservices.service.ServiceCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Contrôleur des services
 * Gère le catalogue de services et les services des prestataires
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private ServiceCatalogService serviceCatalogService;

    // ==================== ENDPOINTS PUBLICS ====================

    /**
     * GET /api/services
     * Liste tous les types de services disponibles
     */
    @GetMapping("/services")
    public ResponseEntity<List<ServiceCatalogDTO>> getAllServices() {
        return ResponseEntity.ok(serviceCatalogService.getAllServices());
    }

    /**
     * GET /api/services/{id}
     * Récupère un service par ID
     */
    @GetMapping("/services/{id}")
    public ResponseEntity<ServiceCatalogDTO> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceCatalogService.getServiceById(id));
    }

    /**
     * GET /api/services/{type}/providers
     * Liste les prestataires pour un type de service
     */
    @GetMapping("/services/{type}/providers")
    public ResponseEntity<List<ProviderServiceDTO>> getProvidersByServiceType(@PathVariable String type) {
        return ResponseEntity.ok(serviceCatalogService.getProvidersByServiceType(type));
    }

    /**
     * GET /api/services/available
     * Liste tous les services disponibles avec leurs prestataires
     */
    @GetMapping("/services/available")
    public ResponseEntity<List<ProviderServiceDTO>> getAllAvailableServices() {
        return ResponseEntity.ok(serviceCatalogService.getAllAvailableProviderServices());
    }

    // ==================== ENDPOINTS PRESTATAIRE ====================

    /**
     * GET /api/provider-services
     * Liste les services du prestataire connecté
     */
    @GetMapping("/provider-services")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<List<ProviderServiceDTO>> getMyServices() {
        return ResponseEntity.ok(serviceCatalogService.getMyServices());
    }

    /**
     * POST /api/provider-services
     * Ajoute un nouveau service pour le prestataire
     */
    @PostMapping("/provider-services")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<ProviderServiceDTO> addService(@RequestBody AddServiceRequest request) {
        return ResponseEntity.ok(serviceCatalogService.addService(
                request.getServiceId(),
                request.getPrice(),
                request.getDescription(),
                request.getExperienceYears()));
    }

    /**
     * PUT /api/provider-services/{id}
     * Met à jour un service du prestataire
     */
    @PutMapping("/provider-services/{id}")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<ProviderServiceDTO> updateService(
            @PathVariable Long id,
            @RequestBody UpdateServiceRequest request) {
        return ResponseEntity.ok(serviceCatalogService.updateService(
                id,
                request.getPrice(),
                request.getDescription(),
                request.getAvailable()));
    }

    /**
     * GET /api/provider-services/{id}
     * Récupère les détails d'un service prestataire
     */
    @GetMapping("/provider-services/{id}")
    public ResponseEntity<ProviderServiceDTO> getProviderServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceCatalogService.getProviderServiceById(id));
    }

    // ==================== DTOs INTERNES ====================

    public static class AddServiceRequest {
        private Long serviceId;
        private BigDecimal price;
        private String description;
        private Integer experienceYears;

        public Long getServiceId() {
            return serviceId;
        }

        public void setServiceId(Long serviceId) {
            this.serviceId = serviceId;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getExperienceYears() {
            return experienceYears;
        }

        public void setExperienceYears(Integer experienceYears) {
            this.experienceYears = experienceYears;
        }
    }

    public static class UpdateServiceRequest {
        private BigDecimal price;
        private String description;
        private Boolean available;

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getAvailable() {
            return available;
        }

        public void setAvailable(Boolean available) {
            this.available = available;
        }
    }
}
