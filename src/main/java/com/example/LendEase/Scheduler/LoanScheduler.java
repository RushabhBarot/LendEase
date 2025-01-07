package com.example.LendEase.Scheduler;

import com.example.LendEase.Services.LoanRequestService;
import com.example.LendEase.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LoanScheduler {

    @Autowired
    private LoanRequestService loanRequestService;

    @Autowired
    private TransactionService transactionService;

    @Scheduled(cron = "0 0 * * * *") // Run every hour
    public void checkLoans() {
        loanRequestService.checkExpiredLoanRequests();

    }
}