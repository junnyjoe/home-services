package com.homeservices.controller;

import com.homeservices.dto.CreateReservationRequest;
import com.homeservices.dto.ReservationDTO;
import com.homeservices.enums.ReservationStatus;
import com.homeservices.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur des réservations
 * Gère la création, consultation et mise à jour des réservations
 */
@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    /**
     * POST /api/reservations
     * Crée une nouvelle réservation (client uniquement)
     */
    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody CreateReservationRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    /**
     * GET /api/reservations/my
     * Liste les réservations de l'utilisateur connecté
     */
    @GetMapping("/my")
    public ResponseEntity<List<ReservationDTO>> getMyReservations() {
        return ResponseEntity.ok(reservationService.getMyReservations());
    }

    /**
     * GET /api/reservations/{id}
     * Récupère une réservation par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    /**
     * PUT /api/reservations/{id}/status
     * Met à jour le statut d'une réservation (prestataire)
     */
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<ReservationDTO> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(reservationService.updateReservationStatus(id, request.getStatus()));
    }

    /**
     * PUT /api/reservations/{id}/cancel
     * Annule une réservation (client)
     */
    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.cancelReservation(id));
    }

    /**
     * GET /api/reservations/pending
     * Liste les réservations en attente pour le prestataire
     */
    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<List<ReservationDTO>> getPendingReservations() {
        return ResponseEntity.ok(reservationService.getPendingReservations());
    }

    /**
     * GET /api/reservations
     * Liste toutes les réservations (admin uniquement)
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRATEUR')")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    // ==================== DTO INTERNE ====================

    public static class UpdateStatusRequest {
        private ReservationStatus status;

        public ReservationStatus getStatus() {
            return status;
        }

        public void setStatus(ReservationStatus status) {
            this.status = status;
        }
    }
}
