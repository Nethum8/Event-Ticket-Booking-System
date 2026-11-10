package com.ticket_booking.app.venue;

import jakarta.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private Long venueId;
    private Long eventId;

    @Column(nullable = false)
    private String seatNumber; // e.g., "A1", "A2", "VIP-01"

    private String seatCategory; // "VIP", "STANDARD", "BALCONY"
    private double price;

    // Status: "AVAILABLE", "LOCKED", "BOOKED"
    private String status;

    private Long bookedByUserId; // Null if seat is available

    // Default Constructor (Required by JPA)
    public Seat() {
        this.status = "AVAILABLE";
    }

    // Parameterized Constructor
    public Seat(Long venueId, Long eventId, String seatNumber, String seatCategory, double price) {
        this.venueId = venueId;
        this.eventId = eventId;
        this.seatNumber = seatNumber;
        this.seatCategory = seatCategory;
        this.price = price;
        this.status = "AVAILABLE";
        this.bookedByUserId = null;
    }

    // Getters and Setters (OOP Encapsulation)
    public Long getSeatId() { return seatId; }
    public void setSeatId(Long seatId) { this.seatId = seatId; }

    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public String getSeatCategory() { return seatCategory; }
    public void setSeatCategory(String seatCategory) { this.seatCategory = seatCategory; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getBookedByUserId() { return bookedByUserId; }
    public void setBookedByUserId(Long bookedByUserId) { this.bookedByUserId = bookedByUserId; }
}