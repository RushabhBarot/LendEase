package com.example.LendEase.DTOs;

import com.example.LendEase.Entities.Enums.TransactionStatus;
import com.example.LendEase.Entities.LoanRequest;
import com.example.LendEase.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;

    private User user;
    private LoanRequest loanRequest;

    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private TransactionType type;
    private TransactionStatus status;
}
