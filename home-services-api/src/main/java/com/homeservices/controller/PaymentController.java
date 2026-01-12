package com.homeservices.controller;

import com.homeservices.dto.PaymentRequest;
import com.homeservices.dto.TransactionDTO;
import com.homeservices.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur des paiements
 * Gère le traitement des paiements et les transactions
 */
@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * POST /api/payments
     * Traite un paiement (client uniquement)
     */
    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<TransactionDTO> processPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    /**
     * GET /api/payments/my
     * Liste les transactions de l'utilisateur connecté
     */
    @GetMapping("/my")
    public ResponseEntity<List<TransactionDTO>> getMyTransactions() {
        return ResponseEntity.ok(paymentService.getMyTransactions());
    }

    /**
     * GET /api/provider/balance
     * Récupère le solde du prestataire
     */
    @GetMapping("/provider/balance")
    @PreAuthorize("hasAuthority('PRESTATAIRE')")
    public ResponseEntity<Map<String, BigDecimal>> getProviderBalance() {
        BigDecimal balance = paymentService.getProviderBalance();
        return ResponseEntity.ok(Map.of("balance", balance));
    }

    /**
     * GET /api/payments
     * Liste toutes les transactions (admin uniquement)
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMINISTRATEUR')")
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(paymentService.getAllTransactions());
    }
}
