package com.example.LendEase.Controllers;

import com.example.LendEase.DTOs.DashboardDTO;
import com.example.LendEase.Services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Get dashboard details for a user
    @GetMapping("/{userId}")
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable Long userId) {
        DashboardDTO dashboardDTO = dashboardService.getDashboardForUser(userId);
        return ResponseEntity.ok(dashboardDTO);
    }
}