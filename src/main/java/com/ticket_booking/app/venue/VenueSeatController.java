package com.ticket_booking.app.venue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/venues")
@CrossOrigin(origins = "*")
public class VenueSeatController {

    @Autowired
    private VenueSeatService venueSeatService;

    // 1. Create a new venue (POST /api/venues)
    @PostMapping
    public ResponseEntity<Venue> addVenue(@RequestBody Venue venue) {
        return ResponseEntity.status(HttpStatus.CREATED).body(venueSeatService.createVenue(venue));
    }

    // 2. Get all venues (GET /api/venues)
    @GetMapping
    public List<Venue> getAllVenues() {
        return venueSeatService.getAllVenues();
    }

    // 3. Configure/Add a seat to an event (POST /api/venues/seats)
    @PostMapping("/seats")
    public ResponseEntity<Seat> addSeat(@RequestBody Seat seat) {
        return ResponseEntity.status(HttpStatus.CREATED).body(venueSeatService.addSeat(seat));
    }

    // 4. Get all seats for an event (GET /api/venues/events/{eventId}/seats)
    @GetMapping("/events/{eventId}/seats")
    public List<Seat> getSeatsForEvent(@PathVariable Long eventId) {
        return venueSeatService.getSeatsByEvent(eventId);
    }

    // 5. Get only AVAILABLE seats for an event (GET /api/venues/events/{eventId}/seats/available)
    @GetMapping("/events/{eventId}/seats/available")
    public List<Seat> getAvailableSeats(@PathVariable Long eventId) {
        return venueSeatService.getAvailableSeatsByEvent(eventId);
    }

    // 6. Allocate/Book a seat for a user (POST /api/venues/seats/{seatId}/allocate?userId=1)
    @PostMapping("/seats/{seatId}/allocate")
    public ResponseEntity<?> allocateSeat(@PathVariable Long seatId, @RequestParam Long userId) {
        try {
            Seat allocated = venueSeatService.allocateSeat(seatId, userId);
            if (allocated == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(allocated);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // 7. Release a booked seat back to AVAILABLE (POST /api/venues/seats/{seatId}/release)
    @PostMapping("/seats/{seatId}/release")
    public ResponseEntity<?> releaseSeat(@PathVariable Long seatId) {
        Seat released = venueSeatService.releaseSeat(seatId);
        if (released == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(released);
    }

    // 8. Delete a venue (DELETE /api/venues/{venueId})
    @DeleteMapping("/{venueId}")
    public ResponseEntity<String> deleteVenue(@PathVariable Long venueId) {
        if (venueSeatService.deleteVenue(venueId)) {
            return ResponseEntity.ok("Venue deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
}