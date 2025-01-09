package com.example.LendEase.Services;

import com.example.LendEase.DTOs.TransactionDTO;
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

    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return convertToDTO(transaction);
    }

    public List<TransactionDTO> getAllTransactions() {
        // Fetch all transactions from the repository and convert to DTOs
        return transactionRepository.findAll().stream()
                .map(transaction -> convertToDTO(transaction))
                .toList();
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setLenderId(transaction.getLender().getId());
        dto.setLoanRequestId(transaction.getLoanRequest().getId());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setDueDate(transaction.getDueDate());
        dto.setStatus(transaction.getStatus().name());
        return dto;
    }
}
