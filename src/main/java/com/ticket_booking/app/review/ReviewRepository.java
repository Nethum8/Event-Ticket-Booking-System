package com.ticket_booking.app.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Find all reviews for a specific event
    List<Review> findByEventId(Long eventId);

    // Find all reviews submitted by a specific user
    List<Review> findByUserId(Long userId);
}