package com.example.LendEase.Repositories;

import com.example.LendEase.Entities.Enums.TransactionStatus;
import com.example.LendEase.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Find transactions by lender ID
    List<Transaction> findByLenderId(Long lenderId);

    // Find transactions by borrower ID (through loan request)
    List<Transaction> findByLoanRequestBorrowerId(Long borrowerId);

    // Find active transactions by borrower ID

    // Find active transactions by lender ID


    // Find overdue transactions (ACTIVE and past due date)
}
