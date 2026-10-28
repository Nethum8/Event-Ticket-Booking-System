package com.ticket_booking.app.review;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private Long userId;

    // Rating value between 1 and 5
    @Column(nullable = false)
    private int rating;

    @Column(length = 1000)
    private String comment;

    private LocalDateTime createdAt;

    // Default Constructor (Required by JPA)
    public Review() {
        this.createdAt = LocalDateTime.now();
    }

    // Parameterized Constructor
    public Review(Long eventId, Long userId, int rating, String comment) {
        this.eventId = eventId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters (OOP Encapsulation)
    public Long getReviewId() { return reviewId; }
    public void setReviewId(Long reviewId) { this.reviewId = reviewId; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}