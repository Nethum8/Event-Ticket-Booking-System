package com.ticket_booking.app.venue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VenueSeatService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private SeatRepository seatRepository;

    // 1. Add a new Venue
    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    // 2. Get all Venues
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    // 3. Add/Configure a single Seat for an Event at a Venue
    public Seat addSeat(Seat seat) {
        if (seat.getStatus() == null || seat.getStatus().isEmpty()) {
            seat.setStatus("AVAILABLE");
        }
        return seatRepository.save(seat);
    }

    // 4. Get all seats for a specific Event
    public List<Seat> getSeatsByEvent(Long eventId) {
        return seatRepository.findByEventId(eventId);
    }

    // 5. Get only AVAILABLE seats for an Event
    public List<Seat> getAvailableSeatsByEvent(Long eventId) {
        return seatRepository.findByEventIdAndStatus(eventId, "AVAILABLE");
    }

    // 6. Allocate / Lock a seat for a specific User
    public synchronized Seat allocateSeat(Long seatId, Long userId) {
        Optional<Seat> seatOpt = seatRepository.findById(seatId);
        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            if (!"AVAILABLE".equalsIgnoreCase(seat.getStatus())) {
                throw new IllegalStateException("Seat " + seat.getSeatNumber() + " is already booked or locked!");
            }
            seat.setStatus("BOOKED");
            seat.setBookedByUserId(userId);
            return seatRepository.save(seat);
        }
        return null;
    }

    // 7. Release a seat (if booking is cancelled)
    public Seat releaseSeat(Long seatId) {
        Optional<Seat> seatOpt = seatRepository.findById(seatId);
        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            seat.setStatus("AVAILABLE");
            seat.setBookedByUserId(null);
            return seatRepository.save(seat);
        }
        return null;
    }

    // 8. Delete a Venue
    public boolean deleteVenue(Long venueId) {
        if (venueRepository.existsById(venueId)) {
            venueRepository.deleteById(venueId);
            return true;
        }
        return false;
    }
}