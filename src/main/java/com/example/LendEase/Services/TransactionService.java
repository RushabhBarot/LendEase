package com.example.LendEase.Services;

import com.example.LendEase.Entities.Enums.LoanRequestStatus;
import com.example.LendEase.Entities.Enums.TransactionStatus;
import com.example.LendEase.Entities.Transaction;
import com.example.LendEase.Repositories.LoanRequestRepository;
import com.example.LendEase.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    public void checkCompletedTransactions() {
        LocalDateTime now = LocalDateTime.now();
        List<Transaction> completedTransactions = transactionRepository.findByStatusAndDueDateBefore(TransactionStatus.ACTIVE, now);

        for (Transaction transaction : completedTransactions) {
            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction.getLoanRequest().setStatus(LoanRequestStatus.COMPLETED);
            transactionRepository.save(transaction);
            loanRequestRepository.save(transaction.getLoanRequest());
        }
    }
}
