package com.ticket_booking.app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. Register a new user (POST /api/users/register)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 2. User Login (POST /api/users/login)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User authenticatedUser = userService.loginUser(email, password);
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password!");
    }

    // 3. Get all users (GET /api/users)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 4. View user profile by ID (GET /api/users/{id})
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 5. Update user profile (PUT /api/users/{id})
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUserProfile(id, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    // 6. Change user role (PUT /api/users/{id}/role?role=ADMIN)
    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestParam String role) {
        User user = userService.assignRole(id, role);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    // 7. Delete user account (DELETE /api/users/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        if (userService.deleteUserAccount(id)) {
            return ResponseEntity.ok("User account deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}