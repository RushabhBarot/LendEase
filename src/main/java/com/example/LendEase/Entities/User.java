package com.example.LendEase.Entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<LoanRequest> getLoanRequests() {
        return loanRequests;
    }

    public void setLoanRequests(List<LoanRequest> loanRequests) {
        this.loanRequests = loanRequests;
    }

    @OneToMany(mappedBy = "lender")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "borrower")
    private List<LoanRequest> loanRequests;
}
