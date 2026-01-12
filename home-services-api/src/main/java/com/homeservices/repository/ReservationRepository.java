package com.homeservices.repository;

import com.homeservices.entity.Reservation;
import com.homeservices.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository pour l'entité Reservation
 * Gère les réservations de services
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Liste les réservations d'un client
     */
    List<Reservation> findByClientIdOrderByCreatedAtDesc(Long clientId);

    /**
     * Liste les réservations pour un prestataire
     */
    @Query("SELECT r FROM Reservation r WHERE r.providerService.provider.id = :providerId ORDER BY r.createdAt DESC")
    List<Reservation> findByProviderId(Long providerId);

    /**
     * Liste les réservations par statut
     */
    List<Reservation> findByStatus(ReservationStatus status);

    /**
     * Compte les réservations par statut
     */
    Long countByStatus(ReservationStatus status);

    /**
     * Liste les réservations d'aujourd'hui
     */
    @Query("SELECT r FROM Reservation r WHERE r.scheduledDate >= :startOfDay AND r.scheduledDate < :endOfDay")
    List<Reservation> findTodayReservations(LocalDateTime startOfDay, LocalDateTime endOfDay);

    /**
     * Compte les réservations d'aujourd'hui
     */
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.scheduledDate >= :startOfDay AND r.scheduledDate < :endOfDay")
    Long countTodayReservations(LocalDateTime startOfDay, LocalDateTime endOfDay);

    /**
     * Liste les réservations récentes (pour admin)
     */
    @Query("SELECT r FROM Reservation r ORDER BY r.createdAt DESC")
    List<Reservation> findRecentReservations();

    /**
     * Liste les réservations en attente pour un prestataire
     */
    @Query("SELECT r FROM Reservation r WHERE r.providerService.provider.id = :providerId AND r.status = :status ORDER BY r.scheduledDate ASC")
    List<Reservation> findByProviderIdAndStatus(Long providerId, ReservationStatus status);

    /**
     * Revenus d'un prestataire (réservations terminées)
     */
    @Query("SELECT r FROM Reservation r WHERE r.providerService.provider.id = :providerId AND r.status = 'TERMINEE' AND r.transaction IS NOT NULL")
    List<Reservation> findCompletedReservationsWithPayment(Long providerId);
}
