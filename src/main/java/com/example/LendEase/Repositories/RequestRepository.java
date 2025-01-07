package com.example.LendEase.Repositories;

import com.example.LendEase.Entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request,Long> {
}
