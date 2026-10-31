package com.ticket_booking.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 1. Register a new user
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email is already registered!");
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("CUSTOMER");
        }
        return userRepository.save(user);
    }

    // 2. Authenticate user login
    public User loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user; // Login successful
            }
        }
        return null; // Invalid email or password
    }

    // 3. Get all users (Admin functionality)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 4. Get user profile by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // 5. Update user profile details
    public User updateUserProfile(Long id, User updatedDetails) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();
            existingUser.setFullName(updatedDetails.getFullName());
            existingUser.setPhoneNumber(updatedDetails.getPhoneNumber());

            // Update password only if a new one is provided
            if (updatedDetails.getPassword() != null && !updatedDetails.getPassword().isEmpty()) {
                existingUser.setPassword(updatedDetails.getPassword());
            }
            return userRepository.save(existingUser);
        }
        return null;
    }

    // 6. Assign or update user role (e.g., CUSTOMER -> ORGANIZER or ADMIN)
    public User assignRole(Long id, String newRole) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();
            existingUser.setRole(newRole.toUpperCase());
            return userRepository.save(existingUser);
        }
        return null;
    }

    // 7. Delete user account
    public boolean deleteUserAccount(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}