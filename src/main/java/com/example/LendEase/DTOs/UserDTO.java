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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getTransactionIds() {
        return transactionIds;
    }

    public void setTransactionIds(List<Long> transactionIds) {
        this.transactionIds = transactionIds;
    }

    public List<Long> getLoanRequestIds() {
        return loanRequestIds;
    }

    public void setLoanRequestIds(List<Long> loanRequestIds) {
        this.loanRequestIds = loanRequestIds;
    }
}

