package com.example.LendEase.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Dashboard {
    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal totalMoneyLent = BigDecimal.ZERO;
    private BigDecimal totalMoneyBorrowed = BigDecimal.ZERO;
    private BigDecimal pendingPayments = BigDecimal.ZERO;
    private BigDecimal expectedIncome = BigDecimal.ZERO;
    private Integer activeTransactions = 0;
    @Version
    private Long version;



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}