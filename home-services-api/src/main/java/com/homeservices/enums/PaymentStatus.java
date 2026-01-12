package com.homeservices.enums;

/**
 * Statuts des paiements
 * - EN_ATTENTE: Paiement initié
 * - REUSSI: Paiement effectué avec succès
 * - ECHOUE: Paiement échoué
 * - REMBOURSE: Paiement remboursé
 */
public enum PaymentStatus {
    EN_ATTENTE("En attente"),
    REUSSI("Réussi"),
    ECHOUE("Échoué"),
    REMBOURSE("Remboursé");

    private final String label;

    PaymentStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
