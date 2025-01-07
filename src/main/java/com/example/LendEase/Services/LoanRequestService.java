package com.example.LendEase.Services;

import com.example.LendEase.DTOs.LoanRequestDTO;
import com.example.LendEase.Entities.Enums.LoanRequestStatus;
import com.example.LendEase.Entities.Enums.TransactionStatus;
import com.example.LendEase.Entities.LoanRequest;
import com.example.LendEase.Entities.Transaction;
import com.example.LendEase.Entities.User;
import com.example.LendEase.Repositories.LoanRequestRepository;
import com.example.LendEase.Repositories.TransactionRepository;
import com.example.LendEase.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanRequestService {

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PoolService poolService;

    public LoanRequestDTO createLoanRequest(LoanRequestDTO loanRequestDTO) {
        User borrower = userRepository.findById(loanRequestDTO.getBorrowerId())
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBorrower(borrower);
        loanRequest.setAmount(loanRequestDTO.getAmount());
        loanRequest.setDurationInDays(loanRequestDTO.getDurationInDays());
        loanRequest.setInterestRate(loanRequestDTO.getInterestRate());
        loanRequest.setCreatedAt(LocalDateTime.now());
        loanRequest.setExpiresAt(loanRequest.getCreatedAt().plusDays(loanRequest.getDurationInDays()));
        loanRequest.setStatus(LoanRequestStatus.PENDING);

        LoanRequest savedLoanRequest = loanRequestRepository.save(loanRequest);
        poolService.addLoanRequestToPool(savedLoanRequest.getId());

        return convertToDTO(savedLoanRequest);
    }

    public LoanRequestDTO getLoanRequestById(Long id) {
        LoanRequest loanRequest = loanRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan request not found"));
        return convertToDTO(loanRequest);
    }

    public List<LoanRequestDTO> getPendingLoanRequests() {
        List<LoanRequest> pendingRequests = loanRequestRepository.findByStatus(LoanRequestStatus.PENDING);
        return pendingRequests.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    @Transactional
    public LoanRequestDTO acceptLoanRequest(Long loanRequestId, Long lenderId) {
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new RuntimeException("Loan request not found"));

        User lender = userRepository.findById(lenderId)
                .orElseThrow(() -> new RuntimeException("Lender not found"));

        if (loanRequest.getStatus() != LoanRequestStatus.PENDING) {
            throw new RuntimeException("Loan request is not available for acceptance");
        }

        loanRequest.setStatus(LoanRequestStatus.ACCEPTED);
        LoanRequest updatedLoanRequest = loanRequestRepository.save(loanRequest);

        Transaction transaction = new Transaction();
        transaction.setLender(lender);
        transaction.setLoanRequest(updatedLoanRequest);
        transaction.setAmount(updatedLoanRequest.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setDueDate(transaction.getTransactionDate().plusDays(updatedLoanRequest.getDurationInDays()));
        transaction.setStatus(TransactionStatus.ACTIVE);

        transactionRepository.save(transaction);

        return convertToDTO(updatedLoanRequest);
    }

    public void checkExpiredLoanRequests() {
        LocalDateTime now = LocalDateTime.now();
        List<LoanRequest> expiredRequests = loanRequestRepository.findByStatusAndExpiresAtBefore(LoanRequestStatus.PENDING, now);

        for (LoanRequest request : expiredRequests) {
            request.setStatus(LoanRequestStatus.CANCELLED);
            loanRequestRepository.save(request);
        }
    }

    private LoanRequestDTO convertToDTO(LoanRequest loanRequest) {
        LoanRequestDTO dto = new LoanRequestDTO();
        dto.setId(loanRequest.getId());
        dto.setBorrowerId(loanRequest.getBorrower().getId());
        dto.setAmount(loanRequest.getAmount());
        dto.setDurationInDays(loanRequest.getDurationInDays());
        dto.setInterestRate(loanRequest.getInterestRate());
        dto.setCreatedAt(loanRequest.getCreatedAt());
        dto.setStatus(loanRequest.getStatus().name());
        return dto;
    }
}
