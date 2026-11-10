package com.ticket_booking.app.venue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    // Find all seats for a specific event
    List<Seat> findByEventId(Long eventId);

    // Find available or booked seats for a specific event
    List<Seat> findByEventIdAndStatus(Long eventId, String status);

    // Find all seats belonging to a venue
    List<Seat> findByVenueId(Long venueId);
}