package com.ticket_booking.app.event;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    private String title;
    private String category; // e.g., "Concert", "Theater", "Sports"
    private String location;
    private LocalDate eventDate;
    private double ticketPrice;
    private int availableSeats;
    private double popularityScore; // Used for Merge Sort by popularity

    // Default Constructor (Required by JPA)
    public Event() {
    }

    // Parameterized Constructor
    public Event(String title, String category, String location, LocalDate eventDate, double ticketPrice, int availableSeats, double popularityScore) {
        this.title = title;
        this.category = category;
        this.location = location;
        this.eventDate = eventDate;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
        this.popularityScore = popularityScore;
    }

    // Getters and Setters (OOP Encapsulation)
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public double getTicketPrice() { return ticketPrice; }
    public void setTicketPrice(double ticketPrice) { this.ticketPrice = ticketPrice; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }

    public double getPopularityScore() { return popularityScore; }
    public void setPopularityScore(double popularityScore) { this.popularityScore = popularityScore; }
}