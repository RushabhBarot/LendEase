package com.example.LendEase.Repositories;

import com.example.LendEase.Entities.Pool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoolRepository extends JpaRepository<Pool,Long> {
}
