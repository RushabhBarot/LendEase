package com.example.LendEase.Services;

import com.example.LendEase.DTOs.UserDTO;
import com.example.LendEase.Entities.User;
import com.example.LendEase.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Method to create a new user
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    // Method to get a user by ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    // Method to get all users
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Method to update an existing user
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Sensitive data should be handled carefully
        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    // Method to delete a user
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Helper method to map the User entity to a UserDTO
    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setTransactionIds(user.getTransactions().stream()
                .map(transaction -> transaction.getId())
                .collect(Collectors.toList()));
        userDTO.setLoanRequestIds(user.getLoanRequests().stream()
                .map(loanRequest -> loanRequest.getId())
                .collect(Collectors.toList()));
        return userDTO;
    }
}
