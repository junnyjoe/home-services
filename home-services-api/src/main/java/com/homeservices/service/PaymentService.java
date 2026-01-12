package com.homeservices.service;

import com.homeservices.dto.PaymentRequest;
import com.homeservices.dto.TransactionDTO;
import com.homeservices.entity.Reservation;
import com.homeservices.entity.Transaction;
import com.homeservices.entity.User;
import com.homeservices.enums.PaymentStatus;
import com.homeservices.enums.ReservationStatus;
import com.homeservices.enums.UserRole;
import com.homeservices.repository.ReservationRepository;
import com.homeservices.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service de gestion des paiements
 * Note: Ce service simule les paiements. Pour une intégration réelle,
 * utilisez Stripe ou un autre provider de paiement.
 */
@Service
public class PaymentService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserService userService;

    /**
     * Traite un paiement (simulation)
     */
    @Transactional
    public TransactionDTO processPayment(PaymentRequest request) {
        User client = userService.getCurrentUser();

        // Vérifier si la réservation existe et appartient au client
        Reservation reservation = reservationRepository.findById(request.getReservationId())
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        if (!reservation.getClient().getId().equals(client.getId())) {
            throw new RuntimeException("Non autorisé");
        }

        // Vérifier si déjà payé
        if (transactionRepository.existsByReservationId(request.getReservationId())) {
            throw new RuntimeException("Cette réservation a déjà été payée");
        }

        // Créer la transaction
        Transaction transaction = new Transaction();
        transaction.setReservation(reservation);
        transaction.setAmount(reservation.getProviderService().getPrice());
        transaction.setPaymentMethod(request.getPaymentMethod());
        transaction.setTransactionDate(LocalDateTime.now());

        // Simulation: Le paiement réussit toujours
        // En production, intégrer avec Stripe ici
        transaction.setStatus(PaymentStatus.REUSSI);
        transaction.setTransactionReference("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        // Mettre à jour le statut de la réservation
        reservation.setStatus(ReservationStatus.CONFIRMEE);
        reservationRepository.save(reservation);

        transaction = transactionRepository.save(transaction);
        return new TransactionDTO(transaction);
    }

    /**
     * Liste les transactions de l'utilisateur courant
     */
    public List<TransactionDTO> getMyTransactions() {
        User user = userService.getCurrentUser();

        if (user.getRole() == UserRole.CLIENT) {
            return transactionRepository.findByClientId(user.getId())
                    .stream()
                    .map(TransactionDTO::new)
                    .collect(Collectors.toList());
        } else if (user.getRole() == UserRole.PRESTATAIRE) {
            return transactionRepository.findByProviderId(user.getId())
                    .stream()
                    .map(TransactionDTO::new)
                    .collect(Collectors.toList());
        }

        return List.of();
    }

    /**
     * Calcule le solde d'un prestataire
     */
    public BigDecimal getProviderBalance() {
        User provider = userService.getCurrentUser();
        return transactionRepository.calculateProviderBalance(provider.getId());
    }

    /**
     * Liste toutes les transactions (admin)
     */
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findRecentTransactions()
                .stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Calcule le revenu total
     */
    public BigDecimal getTotalRevenue() {
        return transactionRepository.calculateTotalRevenue();
    }

    /**
     * Compte les transactions réussies
     */
    public Long countSuccessfulTransactions() {
        return transactionRepository.countByStatus(PaymentStatus.REUSSI);
    }
}
