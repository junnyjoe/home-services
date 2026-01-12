package com.homeservices.dto;

import com.homeservices.entity.ProviderService;
import java.math.BigDecimal;

/**
 * DTO pour les services proposés par les prestataires
 */
public class ProviderServiceDTO {

    private Long id;
    private Long providerId;
    private String providerName;
    private String providerEmail;
    private String providerPhone;
    private Long serviceId;
    private String serviceName;
    private String serviceType;
    private String serviceIcon;
    private BigDecimal price;
    private String description;
    private Boolean available;
    private Integer experienceYears;

    public ProviderServiceDTO() {
    }

    public ProviderServiceDTO(ProviderService ps) {
        this.id = ps.getId();
        this.providerId = ps.getProvider().getId();
        this.providerName = ps.getProvider().getFullName();
        this.providerEmail = ps.getProvider().getEmail();
        this.providerPhone = ps.getProvider().getPhone();
        this.serviceId = ps.getServiceCatalog().getId();
        this.serviceName = ps.getServiceCatalog().getName();
        this.serviceType = ps.getServiceCatalog().getType().name();
        this.serviceIcon = ps.getServiceCatalog().getIcon();
        this.price = ps.getPrice();
        this.description = ps.getDescription();
        this.available = ps.getAvailable();
        this.experienceYears = ps.getExperienceYears();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
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
