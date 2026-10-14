package com.ticket_booking.app.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentTicketController {

    @Autowired
    private PaymentTicketService paymentTicketService;

    // 1. Make a payment and automatically receive an issued E-Ticket (POST /api/payments/pay)
    @PostMapping("/pay")
    public ResponseEntity<?> makePaymentAndIssueTicket(@RequestBody Map<String, Object> payload) {
        try {
            Long bookingId = Long.valueOf(payload.get("bookingId").toString());
            Long userId = Long.valueOf(payload.get("userId").toString());
            Long eventId = Long.valueOf(payload.get("eventId").toString());
            double amount = Double.parseDouble(payload.get("amount").toString());
            String paymentMethod = payload.get("paymentMethod").toString();
            String seatNumbers = payload.get("seatNumbers").toString();

            Payment payment = new Payment(bookingId, userId, amount, paymentMethod);
            ETicket issuedTicket = paymentTicketService.processPaymentAndIssueTicket(payment, eventId, seatNumbers);

            return ResponseEntity.status(HttpStatus.CREATED).body(issuedTicket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: " + e.getMessage());
        }
    }

    // 2. Get all payment records (GET /api/payments)
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentTicketService.getAllPayments();
    }

    // 3. Get all E-Tickets for a specific user (GET /api/payments/tickets/user/{userId})
    @GetMapping("/tickets/user/{userId}")
    public List<ETicket> getUserTickets(@PathVariable Long userId) {
        return paymentTicketService.getTicketsByUser(userId);
    }

    // 4. Verify an E-Ticket using its unique Serial Code (GET /api/payments/tickets/verify/{code})
    @GetMapping("/tickets/verify/{code}")
    public ResponseEntity<?> verifyTicket(@PathVariable String code) {
        Optional<ETicket> ticket = paymentTicketService.verifyTicketByCode(code);
        if (ticket.isPresent()) {
            return ResponseEntity.ok(ticket.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Ticket Code!");
    }

    // 5. Refund a payment and cancel the E-Ticket (POST /api/payments/{paymentId}/refund)
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<?> refundPayment(@PathVariable Long paymentId) {
        Payment refunded = paymentTicketService.refundPayment(paymentId);
        if (refunded != null) {
            return ResponseEntity.ok(refunded);
        }
        return ResponseEntity.notFound().build();
    }
}