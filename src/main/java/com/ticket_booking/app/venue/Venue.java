package com.ticket_booking.app.venue;

import jakarta.persistence.*;

@Entity
@Table(name = "venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long venueId;

    @Column(nullable = false)
    private String venueName;

    @Column(nullable = false)
    private String address;

    private int totalCapacity;
    private String facilities; // e.g., "AC, Parking, Wheelchair Access"

    // Default Constructor (Required by JPA)
    public Venue() {
    }

    // Parameterized Constructor
    public Venue(String venueName, String address, int totalCapacity, String facilities) {
        this.venueName = venueName;
        this.address = address;
        this.totalCapacity = totalCapacity;
        this.facilities = facilities;
    }

    // Getters and Setters (OOP Encapsulation)
    public Long getVenueId() { return venueId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }

    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(int totalCapacity) { this.totalCapacity = totalCapacity; }

    public String getFacilities() { return facilities; }
    public void setFacilities(String facilities) { this.facilities = facilities; }
}