package com.example.LendEase.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private Long userId;
    private BigDecimal totalMoneyLent;
    private BigDecimal totalMoneyBorrowed;
    private BigDecimal pendingPayments;
    private BigDecimal expectedIncome;
    private Integer activeTransactions;
    private List<TransactionDTO> pendingPaymentTransactions;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalMoneyLent() {
        return totalMoneyLent;
    }

    public void setTotalMoneyLent(BigDecimal totalMoneyLent) {
        this.totalMoneyLent = totalMoneyLent;
    }

    public BigDecimal getTotalMoneyBorrowed() {
        return totalMoneyBorrowed;
    }

    public void setTotalMoneyBorrowed(BigDecimal totalMoneyBorrowed) {
        this.totalMoneyBorrowed = totalMoneyBorrowed;
    }

    public BigDecimal getPendingPayments() {
        return pendingPayments;
    }

    public void setPendingPayments(BigDecimal pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public BigDecimal getExpectedIncome() {
        return expectedIncome;
    }

    public void setExpectedIncome(BigDecimal expectedIncome) {
        this.expectedIncome = expectedIncome;
    }

    public Integer getActiveTransactions() {
        return activeTransactions;
    }

    public void setActiveTransactions(Integer activeTransactions) {
        this.activeTransactions = activeTransactions;
    }

    public List<TransactionDTO> getPendingPaymentTransactions() {
        return pendingPaymentTransactions;
    }

    public void setPendingPaymentTransactions(List<TransactionDTO> pendingPaymentTransactions) {
        this.pendingPaymentTransactions = pendingPaymentTransactions;
    }
}
