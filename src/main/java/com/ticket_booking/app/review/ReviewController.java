package com.ticket_booking.app.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 1. Submit a new review (POST /api/reviews)
    @PostMapping
    public ResponseEntity<?> submitReview(@RequestBody Review review) {
        try {
            Review saved = reviewService.addReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 2. Get all reviews for a specific event (GET /api/reviews/event/{eventId})
    @GetMapping("/event/{eventId}")
    public List<Review> getEventReviews(@PathVariable Long eventId) {
        return reviewService.getReviewsByEvent(eventId);
    }

    // 3. Get calculated Popularity Score & Average Rating for an event (GET /api/reviews/event/{eventId}/popularity)
    @GetMapping("/event/{eventId}/popularity")
    public ResponseEntity<Map<String, Object>> getEventPopularity(@PathVariable Long eventId) {
        return ResponseEntity.ok(reviewService.calculateEventPopularity(eventId));
    }

    // 4. Update a review by ID (PUT /api/reviews/{reviewId})
    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId, @RequestBody Review review) {
        try {
            Review updated = reviewService.updateReview(reviewId, review);
            if (updated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 5. Delete a review by ID (DELETE /api/reviews/{reviewId})
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        if (reviewService.deleteReview(reviewId)) {
            return ResponseEntity.ok("Review deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}