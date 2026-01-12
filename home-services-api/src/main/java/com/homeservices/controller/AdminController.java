package com.homeservices.controller;

import com.homeservices.dto.*;
import com.homeservices.enums.PaymentStatus;
import com.homeservices.enums.ReservationStatus;
import com.homeservices.enums.UserRole;
import com.homeservices.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur administrateur
 * Gère le dashboard et les statistiques administrateur
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasAuthority('ADMINISTRATEUR')")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private PaymentService paymentService;

    /**
     * GET /api/admin/stats
     * Récupère les statistiques du dashboard
     */
    @GetMapping("/stats")
    public ResponseEntity<AdminStatsDTO> getStats() {
        AdminStatsDTO stats = new AdminStatsDTO();

        // Statistiques utilisateurs
        stats.setTotalUsers(userService.countByRole(UserRole.CLIENT) +
                userService.countByRole(UserRole.PRESTATAIRE));
        stats.setTotalClients(userService.countByRole(UserRole.CLIENT));
        stats.setTotalProviders(userService.countByRole(UserRole.PRESTATAIRE));

        // Statistiques réservations
        stats.setTotalReservations(reservationService.countByStatus(ReservationStatus.EN_ATTENTE) +
                reservationService.countByStatus(ReservationStatus.CONFIRMEE) +
                reservationService.countByStatus(ReservationStatus.EN_COURS) +
                reservationService.countByStatus(ReservationStatus.TERMINEE) +
                reservationService.countByStatus(ReservationStatus.ANNULEE));
        stats.setPendingReservations(reservationService.countByStatus(ReservationStatus.EN_ATTENTE));
        stats.setCompletedReservations(reservationService.countByStatus(ReservationStatus.TERMINEE));
        stats.setTodayReservations(reservationService.countTodayReservations());

        // Statistiques transactions
        stats.setTotalTransactions(paymentService.countSuccessfulTransactions());
        stats.setTotalRevenue(paymentService.getTotalRevenue());

        return ResponseEntity.ok(stats);
    }

    /**
     * GET /api/admin/users
     * Liste tous les utilisateurs
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * GET /api/admin/reservations
     * Liste toutes les réservations
     */
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    /**
     * GET /api/admin/transactions
     * Liste toutes les transactions
     */
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(paymentService.getAllTransactions());
    }

    /**
     * GET /api/admin/activity
     * Récupère l'activité récente
     */
    @GetMapping("/activity")
    public ResponseEntity<ActivityDTO> getRecentActivity() {
        ActivityDTO activity = new ActivityDTO();
        activity.setRecentReservations(reservationService.getAllReservations().stream().limit(10).toList());
        activity.setRecentTransactions(paymentService.getAllTransactions().stream().limit(10).toList());
        return ResponseEntity.ok(activity);
    }

    // ==================== DTO INTERNE ====================

    public static class ActivityDTO {
        private List<ReservationDTO> recentReservations;
        private List<TransactionDTO> recentTransactions;

        public List<ReservationDTO> getRecentReservations() {
            return recentReservations;
        }

        public void setRecentReservations(List<ReservationDTO> recentReservations) {
            this.recentReservations = recentReservations;
        }

        public List<TransactionDTO> getRecentTransactions() {
            return recentTransactions;
        }

        public void setRecentTransactions(List<TransactionDTO> recentTransactions) {
            this.recentTransactions = recentTransactions;
        }
    }
}
