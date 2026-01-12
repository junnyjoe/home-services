package com.homeservices.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO pour créer un paiement
 */
public class PaymentRequest {

    @NotNull(message = "L'ID de la réservation est obligatoire")
    private Long reservationId;

    @NotBlank(message = "La méthode de paiement est obligatoire")
    private String paymentMethod;

    // Champs optionnels pour simulation de carte
    private String cardNumber;
    private String cardExpiry;
    private String cardCvc;

    public PaymentRequest() {
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public void setCardExpiry(String cardExpiry) {
        this.cardExpiry = cardExpiry;
    }

    public String getCardCvc() {
        return cardCvc;
    }

    public void setCardCvc(String cardCvc) {
        this.cardCvc = cardCvc;
    }
}
