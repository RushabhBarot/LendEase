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
public class RequestDTO {
    private Long id;

    private BigDecimal amount;
    private BigDecimal interestRate;
    private Integer durationInMonths;
    private LocalDateTime createdAt;
    private RequestType type;
    private RequestStatus status;

    private Pool pool;
    private User user;
}
