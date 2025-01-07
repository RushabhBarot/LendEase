package com.example.LendEase.Entities;


import com.example.LendEase.Entities.Enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lender_id")
    private User lender;

    @OneToOne
    @JoinColumn(name = "loan_request_id")
    private LoanRequest loanRequest;

    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private LocalDateTime dueDate;
    private TransactionStatus status;

}