package com.example.LendEase.Repositories;

import com.example.LendEase.Entities.Enums.TransactionStatus;
import com.example.LendEase.Entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByStatusAndDueDateBefore(TransactionStatus transactionStatus, LocalDateTime now);
}
