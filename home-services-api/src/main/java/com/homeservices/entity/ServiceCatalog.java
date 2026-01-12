package com.homeservices.entity;

import com.homeservices.enums.ServiceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité Catalogue de Services
 * Représente les types de services disponibles (Menuiserie, Plomberie,
 * Électricité)
 * 
 * Relations:
 * - Un service peut être proposé par plusieurs prestataires (OneToMany)
 */
@Entity
@Table(name = "service_catalog")
public class ServiceCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du service est obligatoire")
    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ServiceType type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 10)
    private String icon;

    @Column(name = "base_price")
    private Double basePrice;

    // Relations
    @OneToMany(mappedBy = "serviceCatalog", cascade = CascadeType.ALL)
    private List<ProviderService> providerServices = new ArrayList<>();

    // Constructeurs
    public ServiceCatalog() {
    }

    public ServiceCatalog(String name, ServiceType type, String description, String icon, Double basePrice) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.icon = icon;
        this.basePrice = basePrice;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public List<ProviderService> getProviderServices() {
        return providerServices;
    }

    public void setProviderServices(List<ProviderService> providerServices) {
        this.providerServices = providerServices;
    }
}
