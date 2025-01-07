package com.example.LendEase.Entities;

import com.example.LendEase.Entities.Enums.RequestStatus;
import com.example.LendEase.Entities.Enums.RequestType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal amount;
    private Integer durationInMonths;
    private BigDecimal interestRate;
    private RequestType type;
    private LocalDateTime createdAt;
    private RequestStatus status;
}
