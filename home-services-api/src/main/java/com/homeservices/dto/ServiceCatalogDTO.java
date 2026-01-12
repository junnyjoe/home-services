package com.homeservices.dto;

import com.homeservices.entity.ServiceCatalog;
import com.homeservices.enums.ServiceType;

/**
 * DTO pour les services du catalogue
 */
public class ServiceCatalogDTO {

    private Long id;
    private String name;
    private ServiceType type;
    private String description;
    private String icon;
    private Double basePrice;

    public ServiceCatalogDTO() {
    }

    public ServiceCatalogDTO(ServiceCatalog service) {
        this.id = service.getId();
        this.name = service.getName();
        this.type = service.getType();
        this.description = service.getDescription();
        this.icon = service.getIcon();
        this.basePrice = service.getBasePrice();
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
}
