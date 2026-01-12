package com.homeservices.dto;

import com.homeservices.entity.Transaction;
import com.homeservices.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO pour les transactions
 */
public class TransactionDTO {

    private Long id;
    private Long reservationId;
    private String clientName;
    private String providerName;
    private String serviceName;
    private BigDecimal amount;
    private PaymentStatus status;
    private String paymentMethod;
    private String transactionReference;
    private LocalDateTime transactionDate;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction t) {
        this.id = t.getId();
        this.reservationId = t.getReservation().getId();
        this.clientName = t.getReservation().getClient().getFullName();
        this.providerName = t.getReservation().getProviderService().getProvider().getFullName();
        this.serviceName = t.getReservation().getProviderService().getServiceCatalog().getName();
        this.amount = t.getAmount();
        this.status = t.getStatus();
        this.paymentMethod = t.getPaymentMethod();
        this.transactionReference = t.getTransactionReference();
        this.transactionDate = t.getTransactionDate();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
