package com.homeservices.dto;

import com.homeservices.entity.Reservation;
import com.homeservices.enums.ReservationStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO pour les réservations
 */
public class ReservationDTO {

    private Long id;
    private Long clientId;
    private String clientName;
    private String clientEmail;
    private String clientPhone;
    private Long providerServiceId;
    private Long providerId;
    private String providerName;
    private String serviceName;
    private String serviceType;
    private BigDecimal price;
    private LocalDateTime scheduledDate;
    private ReservationStatus status;
    private String notes;
    private String address;
    private LocalDateTime createdAt;
    private Boolean isPaid;

    public ReservationDTO() {
    }

    public ReservationDTO(Reservation r) {
        this.id = r.getId();
        this.clientId = r.getClient().getId();
        this.clientName = r.getClient().getFullName();
        this.clientEmail = r.getClient().getEmail();
        this.clientPhone = r.getClient().getPhone();
        this.providerServiceId = r.getProviderService().getId();
        this.providerId = r.getProviderService().getProvider().getId();
        this.providerName = r.getProviderService().getProvider().getFullName();
        this.serviceName = r.getProviderService().getServiceCatalog().getName();
        this.serviceType = r.getProviderService().getServiceCatalog().getType().name();
        this.price = r.getProviderService().getPrice();
        this.scheduledDate = r.getScheduledDate();
        this.status = r.getStatus();
        this.notes = r.getNotes();
        this.address = r.getAddress();
        this.createdAt = r.getCreatedAt();
        this.isPaid = r.getTransaction() != null;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public Long getProviderServiceId() {
        return providerServiceId;
    }

    public void setProviderServiceId(Long providerServiceId) {
        this.providerServiceId = providerServiceId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
}
