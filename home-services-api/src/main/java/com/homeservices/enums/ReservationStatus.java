package com.homeservices.enums;

/**
 * Statuts des réservations
 * - EN_ATTENTE: Réservation créée, en attente de confirmation
 * - CONFIRMEE: Confirmée par le prestataire
 * - EN_COURS: Prestation en cours
 * - TERMINEE: Prestation terminée
 * - ANNULEE: Réservation annulée
 */
public enum ReservationStatus {
    EN_ATTENTE("En attente"),
    CONFIRMEE("Confirmée"),
    EN_COURS("En cours"),
    TERMINEE("Terminée"),
    ANNULEE("Annulée");

    private final String label;

    ReservationStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
