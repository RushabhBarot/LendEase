package com.example.LendEase.Services;

import com.example.LendEase.DTOs.DashboardDTO;
import com.example.LendEase.DTOs.TransactionDTO;
import com.example.LendEase.Entities.Dashboard;
import com.example.LendEase.Entities.Enums.TransactionStatus;
import com.example.LendEase.Entities.LoanRequest;
import com.example.LendEase.Entities.Transaction;
import com.example.LendEase.Entities.User;
import com.example.LendEase.Repositories.DashboardRepository;
import com.example.LendEase.Repositories.LoanRequestRepository;
import com.example.LendEase.Repositories.TransactionRepository;
import com.example.LendEase.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    public void initializeUserDashboard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Dashboard dashboard = new Dashboard();
        dashboard.setUser(user);
        dashboard.setUserId(userId);
        dashboardRepository.save(dashboard);
    }

    @Transactional
    public void updateDashboardForUser(Long userId) {
        Dashboard dashboard = dashboardRepository.findById(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    Dashboard newDashboard = new Dashboard();
                    newDashboard.setUser(user);
                    newDashboard.setUserId(userId);
                    return newDashboard;                });

        // Total money lent (as a lender)
        List<LoanRequest> lentTransactions = loanRequestRepository.findByLenderId(userId);

        BigDecimal totalLent = lentTransactions.stream()
                .map(LoanRequest::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dashboard.setTotalMoneyLent(totalLent);

        // Total money borrowed (as a borrower)
        List<LoanRequest> borrowedTransactions = loanRequestRepository.findByBorrowerId(userId);

        BigDecimal totalBorrowed = borrowedTransactions.stream()
                .map(LoanRequest::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dashboard.setTotalMoneyBorrowed(totalBorrowed);

        // Pending payments (borrower’s responsibility)
        List<LoanRequest> pendingBorrowerTransactions = loanRequestRepository.findByBorrowerIdAndPayBackIsNull(
                userId);
        BigDecimal pendingPayments = pendingBorrowerTransactions.stream()
                .map(LoanRequest::getAmountToPay) // Assuming amount includes interest; adjust if necessary
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dashboard.setPendingPayments(pendingPayments);

        // Expected income (lender’s expected return)
        List<LoanRequest> activeLenderTransactions = loanRequestRepository.findByLenderIdAndPayBackIsNull(
                userId);
        BigDecimal expectedIncome = activeLenderTransactions.stream()
                .map(LoanRequest::getAmountToPay)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dashboard.setExpectedIncome(expectedIncome);

        // Count active transactions
        int activeAsLender = activeLenderTransactions.size();
        int activeAsBorrower = pendingBorrowerTransactions.size();
        dashboard.setActiveTransactions(activeAsLender + activeAsBorrower);

        dashboardRepository.save(dashboard);
    }

    public DashboardDTO getDashboardForUser(Long userId) {
        // Ensure the dashboard is updated before fetching
        updateDashboardForUser(userId);

        Dashboard dashboard = dashboardRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Dashboard not found for user: " + userId));

        DashboardDTO dto = new DashboardDTO();
        dto.setUserId(dashboard.getUserId());
        dto.setTotalMoneyLent(dashboard.getTotalMoneyLent());
        dto.setTotalMoneyBorrowed(dashboard.getTotalMoneyBorrowed());
        dto.setPendingPayments(dashboard.getPendingPayments());
        dto.setExpectedIncome(dashboard.getExpectedIncome());
        dto.setActiveTransactions(dashboard.getActiveTransactions());

        // Get pending payment transactions for borrower

        return dto;
    }
}
