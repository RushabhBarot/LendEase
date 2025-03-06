package com.example.LendEase.Repositories;

import com.example.LendEase.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsernameAndPassword(String username, String password);
}
