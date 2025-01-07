package com.example.LendEase.Repositories;

import com.example.LendEase.Entities.Enums.TransactionStatus;
import com.example.LendEase.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByStatusAndDueDateBefore(TransactionStatus transactionStatus, LocalDateTime now);
}
