package com.homeservices.entity;

import com.homeservices.enums.ReservationStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entité Réservation
 * Représente une réservation de service par un client
 * 
 * Relations:
 * - Appartient à un client (ManyToOne -> User)
 * - Concerne un service prestataire (ManyToOne -> ProviderService)
 * - Peut avoir une transaction de paiement (OneToOne -> Transaction)
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_service_id", nullable = false)
    private ProviderService providerService;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationStatus status = ReservationStatus.EN_ATTENTE;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(length = 255)
    private String address;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relation avec Transaction
    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Transaction transaction;

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructeurs
    public Reservation() {
    }

    public Reservation(User client, ProviderService providerService, LocalDateTime scheduledDate, String notes,
            String address) {
        this.client = client;
        this.providerService = providerService;
        this.scheduledDate = scheduledDate;
        this.notes = notes;
        this.address = address;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public ProviderService getProviderService() {
        return providerService;
    }

    public void setProviderService(ProviderService providerService) {
        this.providerService = providerService;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
