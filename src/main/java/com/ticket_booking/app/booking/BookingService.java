package com.ticket_booking.app.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private TicketQueue ticketQueue;

    @Autowired
    private BookingRepository bookingRepository;

    // Step 1: Add incoming user booking request to the FIFO Queue
    public String addRequestToQueue(BookingRequest request) {
        request.setStatus("WAITING_IN_QUEUE");
        ticketQueue.enqueue(request);
        return "Booking added to FIFO Queue! Current Queue Position: " + ticketQueue.getSize();
    }

    // Step 2: Dequeue the first request in line (FIFO), confirm it, and save to the database
    public BookingRequest processNextBooking() {
        BookingRequest nextRequest = ticketQueue.dequeue();
        if (nextRequest == null) {
            return null; // The queue is currently empty
        }
        nextRequest.setStatus("CONFIRMED");
        return bookingRepository.save(nextRequest); // Persist confirmed booking to MySQL
    }

    // Step 3: Retrieve all pending booking requests currently waiting in the queue
    public List<BookingRequest> viewCurrentQueue() {
        return ticketQueue.getAllInQueue();
    }

    // Step 4: Retrieve all confirmed bookings saved in the database
    public List<BookingRequest> getConfirmedBookings() {
        return bookingRepository.findAll();
    }
}