package com.ticket_booking.app.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by email for login and duplicate checking
    Optional<User> findByEmail(String email);

    // Check if an email is already registered
    boolean existsByEmail(String email);
}