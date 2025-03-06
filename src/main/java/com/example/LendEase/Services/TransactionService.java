package com.example.LendEase.Services;

import com.example.LendEase.DTOs.TransactionDTO;
import com.example.LendEase.Entities.Enums.LoanRequestStatus;
import com.example.LendEase.Entities.Enums.TransactionStatus;
import com.example.LendEase.Entities.LoanRequest;
import com.example.LendEase.Entities.Transaction;
import com.example.LendEase.Entities.User;
import com.example.LendEase.Repositories.LoanRequestRepository;
import com.example.LendEase.Repositories.TransactionRepository;
import com.example.LendEase.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new transaction when a lender funds a loan request
    @Transactional
    public TransactionDTO createTransaction(Long lenderId, Long loanRequestId, BigDecimal amount, LocalDateTime dueDate) {
        User lender = userRepository.findById(lenderId)
                .orElseThrow(() -> new RuntimeException("Lender not found"));

        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new RuntimeException("Loan request not found"));

        // Ensure the loan request is still open for funding
        if (loanRequest.getStatus() != LoanRequestStatus.PENDING) {
            throw new RuntimeException("Loan request is not available for funding");
        }

        Transaction transaction = new Transaction();
        transaction.setLender(lender);
        transaction.setLoanRequest(loanRequest);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        // Update loan request status if fully funded
//        loanRequest.setStatus(LoanRequestStatus.FUNDED);
        loanRequestRepository.save(loanRequest);

        return convertToDTO(transaction);
    }



    // Get a transaction by ID
    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        return convertToDTO(transaction);
    }

    // Get all transactions
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get transactions for a lender
    public List<TransactionDTO> getTransactionsByLender(Long lenderId) {
        return transactionRepository.findByLenderId(lenderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get transactions for a borrower
    public List<TransactionDTO> getTransactionsByBorrower(Long borrowerId) {
        return transactionRepository.findByLoanRequestBorrowerId(borrowerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get active transactions where the user is a lender


    // Get active transactions where the user is a borrower


    // Convert Entity to DTO
    public TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setLenderId(transaction.getLender().getId());
        dto.setLoanRequestId(transaction.getLoanRequest().getId());
        dto.setAmount(transaction.getAmount());
        dto.setTransactionDate(transaction.getTransactionDate());
        return dto;
    }
}
