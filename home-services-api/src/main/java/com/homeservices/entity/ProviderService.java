package com.homeservices.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Entité Service Prestataire
 * Représente un service proposé par un prestataire avec son prix personnalisé
 * 
 * Relations:
 * - Appartient à un prestataire (ManyToOne -> User)
 * - Appartient à un catalogue de service (ManyToOne -> ServiceCatalog)
 */
@Entity
@Table(name = "provider_services", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "service_id" })
})
public class ProviderService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceCatalog serviceCatalog;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être positif")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean available = true;

    @Column(name = "experience_years")
    private Integer experienceYears;

    // Constructeurs
    public ProviderService() {
    }

    public ProviderService(User provider, ServiceCatalog serviceCatalog, BigDecimal price, String description) {
        this.provider = provider;
        this.serviceCatalog = serviceCatalog;
        this.price = price;
        this.description = description;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getProvider() {
        return provider;
    }

    public void setProvider(User provider) {
        this.provider = provider;
    }

    public ServiceCatalog getServiceCatalog() {
        return serviceCatalog;
    }

    public void setServiceCatalog(ServiceCatalog serviceCatalog) {
        this.serviceCatalog = serviceCatalog;
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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
}
