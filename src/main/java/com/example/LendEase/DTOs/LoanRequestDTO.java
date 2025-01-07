package com.example.LendEase.DTOs;


import com.example.LendEase.Entities.Pool;
import com.example.LendEase.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequestDTO {
    private Long id;
    private Long borrowerId;
    private BigDecimal amount;
    private Integer durationInDays;
    private BigDecimal interestRate;
    private LocalDateTime createdAt;
    private String status;
}
