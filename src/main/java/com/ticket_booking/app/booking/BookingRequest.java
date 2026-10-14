package com.ticket_booking.app.booking;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class BookingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    private Long userId;
    private Long eventId;
    private int ticketCount;
    private String status; // "PENDING_IN_QUEUE", "CONFIRMED", "CANCELLED"
    private LocalDateTime requestTime;

    // Default Constructor (Required by JPA)
    public BookingRequest() {
        this.requestTime = LocalDateTime.now();
        this.status = "PENDING_IN_QUEUE";
    }

    // Parameterized Constructor
    public BookingRequest(Long userId, Long eventId, int ticketCount) {
        this.userId = userId;
        this.eventId = eventId;
        this.ticketCount = ticketCount;
        this.status = "PENDING_IN_QUEUE";
        this.requestTime = LocalDateTime.now();
    }

    // Getters and Setters (OOP Encapsulation)
    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public int getTicketCount() { return ticketCount; }
    public void setTicketCount(int ticketCount) { this.ticketCount = ticketCount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getRequestTime() { return requestTime; }
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }
}