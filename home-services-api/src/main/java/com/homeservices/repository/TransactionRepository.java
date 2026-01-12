package com.homeservices.repository;

import com.homeservices.entity.Transaction;
import com.homeservices.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Transaction
 * Gère les transactions de paiement
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Recherche une transaction par réservation
     */
    Optional<Transaction> findByReservationId(Long reservationId);

    /**
     * Vérifie si une réservation a été payée
     */
    Boolean existsByReservationId(Long reservationId);

    /**
     * Liste les transactions par statut
     */
    List<Transaction> findByStatus(PaymentStatus status);

    /**
     * Liste les transactions d'un client
     */
    @Query("SELECT t FROM Transaction t WHERE t.reservation.client.id = :clientId ORDER BY t.transactionDate DESC")
    List<Transaction> findByClientId(Long clientId);

    /**
     * Liste les transactions d'un prestataire
     */
    @Query("SELECT t FROM Transaction t WHERE t.reservation.providerService.provider.id = :providerId ORDER BY t.transactionDate DESC")
    List<Transaction> findByProviderId(Long providerId);

    /**
     * Calcul du solde d'un prestataire (transactions réussies)
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.reservation.providerService.provider.id = :providerId AND t.status = 'REUSSI'")
    BigDecimal calculateProviderBalance(Long providerId);

    /**
     * Calcul du revenu total
     */
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.status = 'REUSSI'")
    BigDecimal calculateTotalRevenue();

    /**
     * Nombre de transactions réussies
     */
    Long countByStatus(PaymentStatus status);

    /**
     * Transactions récentes (pour admin)
     */
    @Query("SELECT t FROM Transaction t ORDER BY t.transactionDate DESC")
    List<Transaction> findRecentTransactions();
}
