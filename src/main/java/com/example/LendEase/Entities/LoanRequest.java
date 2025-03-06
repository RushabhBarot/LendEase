package com.example.LendEase.Entities;

import com.example.LendEase.Entities.Enums.LoanRequestStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class LoanRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private User borrower;

    @ManyToOne
    @JoinColumn(name = "lender_id")
    private  User lender = null;

    private BigDecimal amount;

    public Transaction getForward() {
        return forward;
    }

    public void setForward(Transaction forward) {
        this.forward = forward;
    }

    public Transaction getPayBack() {
        return payBack;
    }

    public void setPayBack(Transaction payBack) {
        this.payBack = payBack;
    }

    private Integer durationInMonths;
    private BigDecimal interestRate;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LoanRequestStatus status;

    private BigDecimal amountToPay = null;

    @OneToOne
    @JoinColumn(name = "forward")
    private Transaction forward=null;

    @OneToOne
    @JoinColumn(name = "payBack")
    private Transaction payBack=null;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }



    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


    public LoanRequestStatus getStatus() {
        return status;
    }

    public void setStatus(LoanRequestStatus status) {
        this.status = status;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    public User getLender() {
        return lender;
    }

    public void setLender(User lender) {
        this.lender = lender;
    }

    public Integer getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(Integer durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

    @ManyToOne
    @JoinColumn(name = "pool_id")
    private Pool pool;


}
