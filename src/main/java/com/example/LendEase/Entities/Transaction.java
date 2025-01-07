package com.example.LendEase.Entities;

import com.example.LendEase.Entities.Enums.TransactionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "request_id")
    private LoanRequest loanRequest;

    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private TransactionType type;
    private TransactionStatus status;
}
