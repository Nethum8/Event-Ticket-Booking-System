package com.ticket_booking.app.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // 1. Add a new booking request to the FIFO queue (POST)
    @PostMapping("/enqueue")
    public ResponseEntity<String> requestBooking(@RequestBody BookingRequest request) {
        String message = bookingService.addRequestToQueue(request);
        return ResponseEntity.ok(message);
    }

    // 2. View all pending booking requests currently in the queue in FIFO order (GET)
    @GetMapping("/queue")
    public List<BookingRequest> getQueue() {
        return bookingService.viewCurrentQueue();
    }

    // 3. Process and confirm the next booking request at the front of the queue (POST)
    @PostMapping("/process-next")
    public ResponseEntity<?> processNext() {
        BookingRequest processed = bookingService.processNextBooking();
        if (processed == null) {
            return ResponseEntity.badRequest().body("Queue is empty! No bookings to process.");
        }
        return ResponseEntity.ok(processed);
    }

    // 4. Retrieve all confirmed bookings saved in the database (GET)
    @GetMapping("/confirmed")
    public List<BookingRequest> getAllConfirmed() {
        return bookingService.getConfirmedBookings();
    }
}