package com.example.LendEase.DTOs;


import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password; // Include only if necessary; avoid sending sensitive data in responses
    private List<Long> transactionIds; // List of transaction IDs associated with the user
    private List<Long> loanRequestIds; // List of loan request IDs associated with the user
}

