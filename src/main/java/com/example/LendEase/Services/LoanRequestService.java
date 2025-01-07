package com.example.LendEase.Services;

import com.example.LendEase.DTOs.LoanRequestDTO;
import com.example.LendEase.Entities.Enums.LoanRequestStatus;
import com.example.LendEase.Entities.LoanRequest;
import com.example.LendEase.Entities.User;
import com.example.LendEase.Repositories.LoanRequestRepository;
import com.example.LendEase.Repositories.TransactionRepository;
import com.example.LendEase.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
