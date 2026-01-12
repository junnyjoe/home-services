package com.homeservices.service;

import com.homeservices.dto.CreateReservationRequest;
import com.homeservices.dto.ReservationDTO;
import com.homeservices.entity.ProviderService;
import com.homeservices.entity.Reservation;
import com.homeservices.entity.User;
import com.homeservices.enums.ReservationStatus;
import com.homeservices.enums.UserRole;
import com.homeservices.repository.ProviderServiceRepository;
import com.homeservices.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des réservations
 */
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ProviderServiceRepository providerServiceRepository;

    @Autowired
    private UserService userService;

    /**
     * Crée une nouvelle réservation (client)
     */
    @Transactional
    public ReservationDTO createReservation(CreateReservationRequest request) {
        User client = userService.getCurrentUser();

        ProviderService providerService = providerServiceRepository.findById(request.getProviderServiceId())
                .orElseThrow(() -> new RuntimeException("Service prestataire non trouvé"));

        if (!providerService.getAvailable()) {
            throw new RuntimeException("Ce service n'est pas disponible");
        }

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setProviderService(providerService);
        reservation.setScheduledDate(request.getScheduledDate());
        reservation.setNotes(request.getNotes());
        reservation.setAddress(request.getAddress() != null ? request.getAddress() : client.getAddress());
        reservation.setStatus(ReservationStatus.EN_ATTENTE);

        reservation = reservationRepository.save(reservation);
        return new ReservationDTO(reservation);
    }

    /**
     * Liste les réservations de l'utilisateur courant
     */
    public List<ReservationDTO> getMyReservations() {
        User user = userService.getCurrentUser();

        if (user.getRole() == UserRole.CLIENT) {
            return reservationRepository.findByClientIdOrderByCreatedAtDesc(user.getId())
                    .stream()
                    .map(ReservationDTO::new)
                    .collect(Collectors.toList());
        } else if (user.getRole() == UserRole.PRESTATAIRE) {
            return reservationRepository.findByProviderId(user.getId())
                    .stream()
                    .map(ReservationDTO::new)
                    .collect(Collectors.toList());
        }

        return List.of();
    }

    /**
     * Récupère une réservation par ID
     */
    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        User user = userService.getCurrentUser();

        // Vérifier l'accès
        if (user.getRole() != UserRole.ADMINISTRATEUR) {
            boolean isClient = reservation.getClient().getId().equals(user.getId());
            boolean isProvider = reservation.getProviderService().getProvider().getId().equals(user.getId());
            if (!isClient && !isProvider) {
                throw new RuntimeException("Non autorisé");
            }
        }

        return new ReservationDTO(reservation);
    }

    /**
     * Met à jour le statut d'une réservation (prestataire)
     */
    @Transactional
    public ReservationDTO updateReservationStatus(Long id, ReservationStatus status) {
        User provider = userService.getCurrentUser();

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        // Vérifier que le prestataire est bien celui de la réservation
        if (!reservation.getProviderService().getProvider().getId().equals(provider.getId())) {
            throw new RuntimeException("Non autorisé");
        }

        reservation.setStatus(status);
        reservation = reservationRepository.save(reservation);
        return new ReservationDTO(reservation);
    }

    /**
     * Annule une réservation (client)
     */
    @Transactional
    public ReservationDTO cancelReservation(Long id) {
        User client = userService.getCurrentUser();

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        // Vérifier que le client est bien celui de la réservation
        if (!reservation.getClient().getId().equals(client.getId())) {
            throw new RuntimeException("Non autorisé");
        }

        if (reservation.getStatus() == ReservationStatus.TERMINEE) {
            throw new RuntimeException("Impossible d'annuler une réservation terminée");
        }

        reservation.setStatus(ReservationStatus.ANNULEE);
        reservation = reservationRepository.save(reservation);
        return new ReservationDTO(reservation);
    }

    /**
     * Liste toutes les réservations (admin)
     */
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findRecentReservations()
                .stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Compte les réservations par statut
     */
    public Long countByStatus(ReservationStatus status) {
        return reservationRepository.countByStatus(status);
    }

    /**
     * Compte les réservations d'aujourd'hui
     */
    public Long countTodayReservations() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return reservationRepository.countTodayReservations(startOfDay, endOfDay);
    }

    /**
     * Liste les réservations en attente pour un prestataire
     */
    public List<ReservationDTO> getPendingReservations() {
        User provider = userService.getCurrentUser();
        return reservationRepository.findByProviderIdAndStatus(provider.getId(), ReservationStatus.EN_ATTENTE)
                .stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());
    }
}
