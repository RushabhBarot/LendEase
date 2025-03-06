package com.example.LendEase.Repositories;

import com.example.LendEase.Entities.Enums.LoanRequestStatus;
import com.example.LendEase.Entities.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest,Long> {
    List<LoanRequest> findByStatus(LoanRequestStatus loanRequestStatus);

    List<LoanRequest> findByStatusAndCreatedAtBefore(LoanRequestStatus loanRequestStatus, LocalDateTime now);

    List<LoanRequest> findByBorrowerIdAndPayBackIsNull(Long borrowerId);

    List<LoanRequest> findByLenderIdAndPayBackIsNull(Long lenderId);

    List<LoanRequest> findByLenderId(Long lenderId);

    List<LoanRequest> findByBorrowerId(Long borrowerId);

    @NativeQuery("select * from loan_request where (borrowerId=?1 or lenderId=?1) and payBack=null")
    List<LoanRequest>getAllPendindLoan(Long userId);

}
