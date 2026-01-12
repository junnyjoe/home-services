package com.homeservices.dto;

import java.math.BigDecimal;

/**
 * DTO pour les statistiques du dashboard admin
 */
public class AdminStatsDTO {

    private Long totalUsers;
    private Long totalClients;
    private Long totalProviders;
    private Long totalReservations;
    private Long pendingReservations;
    private Long completedReservations;
    private Long totalTransactions;
    private BigDecimal totalRevenue;
    private Long todayReservations;

    public AdminStatsDTO() {
    }

    // Getters et Setters
    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getTotalClients() {
        return totalClients;
    }

    public void setTotalClients(Long totalClients) {
        this.totalClients = totalClients;
    }

    public Long getTotalProviders() {
        return totalProviders;
    }

    public void setTotalProviders(Long totalProviders) {
        this.totalProviders = totalProviders;
    }

    public Long getTotalReservations() {
        return totalReservations;
    }

    public void setTotalReservations(Long totalReservations) {
        this.totalReservations = totalReservations;
    }

    public Long getPendingReservations() {
        return pendingReservations;
    }

    public void setPendingReservations(Long pendingReservations) {
        this.pendingReservations = pendingReservations;
    }

    public Long getCompletedReservations() {
        return completedReservations;
    }

    public void setCompletedReservations(Long completedReservations) {
        this.completedReservations = completedReservations;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getTodayReservations() {
        return todayReservations;
    }

    public void setTodayReservations(Long todayReservations) {
        this.todayReservations = todayReservations;
    }
}
