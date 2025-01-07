package com.example.LendEase.Controllers;

import com.example.LendEase.DTOs.LoanRequestDTO;
import com.example.LendEase.Services.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("requests")
public class LoanRequestController {

    @Autowired
    private LoanRequestService loanRequestService;

    @PostMapping
    public ResponseEntity<LoanRequestDTO> createLoanRequest(@RequestBody LoanRequestDTO loanRequestDTO) {
        LoanRequestDTO createdLoanRequest = loanRequestService.createLoanRequest(loanRequestDTO);
        return ResponseEntity.ok(createdLoanRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanRequestDTO> getLoanRequest(@PathVariable Long id) {
        LoanRequestDTO loanRequest = loanRequestService.getLoanRequestById(id);
        return ResponseEntity.ok(loanRequest);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<LoanRequestDTO>> getPendingLoanRequests() {
        List<LoanRequestDTO> pendingRequests = loanRequestService.getPendingLoanRequests();
        return ResponseEntity.ok(pendingRequests);
    }

    @PostMapping("/{borrowerId}/accept/{lenderId}")
    public ResponseEntity<LoanRequestDTO> acceptLoanRequest(@PathVariable Long borrowerId, @PathVariable Long lenderId) {
        LoanRequestDTO acceptedLoanRequest = loanRequestService.acceptLoanRequest(borrowerId, lenderId);
        return ResponseEntity.ok(acceptedLoanRequest);
    }
}
