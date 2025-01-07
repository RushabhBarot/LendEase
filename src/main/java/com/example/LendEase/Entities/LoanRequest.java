package com.example.LendEase.Entities;

import com.example.LendEase.Entities.Enums.LoanRequestStatus;
import jakarta.persistence.*;
import lombok.Data;

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

    private BigDecimal amount;
    private Integer durationInDays;
    private BigDecimal interestRate;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LoanRequestStatus status;

    @ManyToOne
    @JoinColumn(name = "pool_id")
    private Pool pool;


}
