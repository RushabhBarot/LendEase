package com.example.LendEase.Repositories;

import com.example.LendEase.Entities.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<LoanRequest,Long> {
}
