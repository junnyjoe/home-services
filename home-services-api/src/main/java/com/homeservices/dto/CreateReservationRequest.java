package com.homeservices.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * DTO pour créer une réservation
 */
public class CreateReservationRequest {

    @NotNull(message = "L'ID du service prestataire est obligatoire")
    private Long providerServiceId;

    @NotNull(message = "La date de rendez-vous est obligatoire")
    private LocalDateTime scheduledDate;

    private String notes;
    private String address;

    public CreateReservationRequest() {
    }

    public Long getProviderServiceId() {
        return providerServiceId;
    }

    public void setProviderServiceId(Long providerServiceId) {
        this.providerServiceId = providerServiceId;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDateTime scheduledDate) {
        this.scheduledDate = scheduledDate;
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
}
