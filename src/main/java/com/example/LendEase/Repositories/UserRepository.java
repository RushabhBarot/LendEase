package com.example.LendEase.Repositories;

import com.example.LendEase.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
