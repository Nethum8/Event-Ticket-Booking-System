package com.ticket_booking.app.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    // 1. Submit a new review and rating (validates 1-5 star range)
    public Review addReview(Review review) {
        if (review.getRating() < 1 || review.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5 stars!");
        }
        return reviewRepository.save(review);
    }

    // 2. Get all reviews for a specific event
    public List<Review> getReviewsByEvent(Long eventId) {
        return reviewRepository.findByEventId(eventId);
    }

    // 3. Calculate Popularity Score and Average Rating for an Event
    public Map<String, Object> calculateEventPopularity(Long eventId) {
        List<Review> reviews = reviewRepository.findByEventId(eventId);
        int totalReviews = reviews.size();

        double averageRating = 0.0;
        if (totalReviews > 0) {
            int sum = 0;
            for (Review r : reviews) {
                sum += r.getRating();
            }
            averageRating = (double) sum / totalReviews;
            // Round to 2 decimal places
            averageRating = Math.round(averageRating * 100.0) / 100.0;
        }

        // Popularity Metric Formula: Average Rating + (0.1 * Total Reviews)
        double popularityScore = Math.round((averageRating + (totalReviews * 0.1)) * 100.0) / 100.0;

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("eventId", eventId);
        metrics.put("totalReviews", totalReviews);
        metrics.put("averageRating", averageRating);
        metrics.put("popularityScore", popularityScore);
        return metrics;
    }

    // 4. Update an existing review
    public Review updateReview(Long reviewId, Review updatedDetails) {
        Optional<Review> existingOpt = reviewRepository.findById(reviewId);
        if (existingOpt.isPresent()) {
            if (updatedDetails.getRating() < 1 || updatedDetails.getRating() > 5) {
                throw new IllegalArgumentException("Rating must be between 1 and 5 stars!");
            }
            Review existing = existingOpt.get();
            existing.setRating(updatedDetails.getRating());
            existing.setComment(updatedDetails.getComment());
            return reviewRepository.save(existing);
        }
        return null;
    }

    // 5. Delete a review
    public boolean deleteReview(Long reviewId) {
        if (reviewRepository.existsById(reviewId)) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }
}