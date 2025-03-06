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

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Autowired
    private DashboardService dashboardService;

    public LoanRequestDTO createLoanRequest(LoanRequestDTO loanRequestDTO) {
        User borrower = userRepository.findById(loanRequestDTO.getBorrowerId())
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBorrower(borrower);
        loanRequest.setAmount(loanRequestDTO.getAmount());
        loanRequest.setDurationInMonths(loanRequestDTO.getDurationInMonths());
        loanRequest.setInterestRate(loanRequestDTO.getInterestRate());
        loanRequest.setCreatedAt(LocalDateTime.now());
        loanRequest.setStatus(LoanRequestStatus.PENDING);

        // Calculate amount to pay using Simple Interest formula
        BigDecimal principal = loanRequest.getAmount();
        BigDecimal annualRate = loanRequest.getInterestRate();
        // Convert months to years (12 months in a year)
        BigDecimal timeInYears = BigDecimal.valueOf(loanRequest.getDurationInMonths()).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        // Interest = (P * R * T) / 100
        BigDecimal interest = principal.multiply(annualRate).multiply(timeInYears).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // Total amount to pay = Principal + Interest
        BigDecimal amountToPay = principal.add(interest);
        System.out.println(amountToPay);
        loanRequest.setAmountToPay(amountToPay);

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

        // Add debug logging to see the actual status
        System.out.println("Current loan request status: " + loanRequest.getStatus());

        // Use equals() for safer comparison
        if (!LoanRequestStatus.PENDING.equals(loanRequest.getStatus())) {
            throw new RuntimeException("Loan request is not available for acceptance. Current status: " + loanRequest.getStatus());
        }

        // Set the lender and update the status
        loanRequest.setLender(lender);
        loanRequest.setStatus(LoanRequestStatus.ACCEPTED);
        BigDecimal principal = loanRequest.getAmount();
        BigDecimal annualRate = loanRequest.getInterestRate();
        // Convert months to years (12 months in a year)
        BigDecimal timeInYears = BigDecimal.valueOf(loanRequest.getDurationInMonths()).divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);

        // Interest = (P * R * T) / 100
        BigDecimal interest = principal.multiply(annualRate).multiply(timeInYears).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        // Total amount to pay = Principal + Interest
        BigDecimal amountToPay = principal.add(interest);
        loanRequest.setAmountToPay(amountToPay);

        LoanRequest updatedLoanRequest = loanRequestRepository.save(loanRequest);

        // Create a transaction for the accepted loan
        Transaction transaction = new Transaction();
        transaction.setLender(lender);
        transaction.setBorrower(loanRequest.getBorrower());
        transaction.setLoanRequest(updatedLoanRequest);
        transaction.setAmount(updatedLoanRequest.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());


        transactionRepository.save(transaction);
        loanRequest.setForward(transaction);

        dashboardService.updateDashboardForUser(updatedLoanRequest.getBorrower().getId());
        dashboardService.updateDashboardForUser(updatedLoanRequest.getLender().getId());

        return convertToDTO(updatedLoanRequest);
    }


    public void checkExpiredLoanRequests() {
        LocalDateTime now = LocalDateTime.now();
        List<LoanRequest> expiredRequests = loanRequestRepository.findByStatusAndCreatedAtBefore(LoanRequestStatus.PENDING, now);

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
        dto.setDurationInMonths(loanRequest.getDurationInMonths());
        dto.setInterestRate(loanRequest.getInterestRate());
        dto.setCreatedAt(loanRequest.getCreatedAt());
        dto.setStatus(loanRequest.getStatus().name());
        dto.setAmountToPay(loanRequest.getAmountToPay());
        return dto;
    }

    public LoanRequestDTO repayLoan(Long loanRequestId, Long borrowerId){
        LoanRequest saved;
       try {
           LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                   .orElseThrow(() -> new RuntimeException("Loan request not found"));


           User borrower = userRepository.findById(borrowerId)
                   .orElseThrow(() -> new RuntimeException("Borrower not found"));

           User lender = loanRequest.getLender();

           BigDecimal amountToPay = loanRequest.getAmountToPay();

           Transaction transaction = new Transaction();
           transaction.setLender(borrower);
           transaction.setBorrower(lender);
           transaction.setAmount(amountToPay);
           transaction.setTransactionDate(LocalDateTime.now());

           loanRequest.setStatus(LoanRequestStatus.COMPLETED);
           loanRequest.setPayBack(transaction);
           transactionRepository.save(transaction);
           saved = loanRequestRepository.save(loanRequest);

           dashboardService.updateDashboardForUser(saved.getBorrower().getId());
           dashboardService.updateDashboardForUser(saved.getLender().getId());

       } catch (Exception e) {
           throw new RuntimeException();
       }
        return convertToDTO(saved);
    }
}
