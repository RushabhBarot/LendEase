package com.example.LendEase.Services;

import com.example.LendEase.Entities.LoanRequest;
import com.example.LendEase.Entities.Pool;
import com.example.LendEase.Repositories.LoanRequestRepository;
import com.example.LendEase.Repositories.PoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoolService {
    @Autowired
    private PoolRepository poolRepository;

    @Autowired
    private LoanRequestRepository loanRequestRepository;

    public void addLoanRequestToPool(Long loanRequestId) {
        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new RuntimeException("Loan request not found"));

        Pool pool = poolRepository.findById(1L)
                .orElseGet(() -> {
                    Pool newPool = new Pool();
                    return poolRepository.save(newPool);
                });

        loanRequest.setPool(pool);
        loanRequestRepository.save(loanRequest);
    }
}
