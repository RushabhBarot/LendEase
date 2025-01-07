package com.example.LendEase.Repositories;

import com.example.LendEase.Entities.Enums.LoanRequestStatus;
import com.example.LendEase.Entities.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest,Long> {
    List<LoanRequest> findByStatus(LoanRequestStatus loanRequestStatus);

    List<LoanRequest> findByStatusAndExpiresAtBefore(LoanRequestStatus loanRequestStatus, LocalDateTime now);
}
