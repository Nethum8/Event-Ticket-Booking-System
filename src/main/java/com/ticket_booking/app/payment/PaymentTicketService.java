package com.ticket_booking.app.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentTicketService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ETicketRepository eTicketRepository;

    // 1. Process payment and automatically generate an E-Ticket
    public ETicket processPaymentAndIssueTicket(Payment paymentRequest, Long eventId, String seatNumbers) {
        if (paymentRequest.getAmount() <= 0) {
            throw new IllegalArgumentException("Payment amount must be greater than zero!");
        }

        // Save completed payment to database
        paymentRequest.setPaymentStatus("COMPLETED");
        Payment savedPayment = paymentRepository.save(paymentRequest);

        // Automatically generate and issue E-Ticket
        ETicket newTicket = new ETicket(
                savedPayment.getPaymentId(),
                savedPayment.getBookingId(),
                savedPayment.getUserId(),
                eventId,
                seatNumbers
        );
        return eTicketRepository.save(newTicket);
    }

    // 2. Get all payments (Admin view)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // 3. Get all E-Tickets issued to a specific user
    public List<ETicket> getTicketsByUser(Long userId) {
        return eTicketRepository.findByUserId(userId);
    }

    // 4. Verify an E-Ticket by its unique serial code (e.g., at the event entrance)
    public Optional<ETicket> verifyTicketByCode(String serialCode) {
        return eTicketRepository.findByTicketSerialCode(serialCode);
    }

    // 5. Refund a payment and cancel the associated E-Ticket
    public Payment refundPayment(Long paymentId) {
        Optional<Payment> paymentOpt = paymentRepository.findById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            payment.setPaymentStatus("REFUNDED");
            Payment updatedPayment = paymentRepository.save(payment);

            // Cancel the linked E-Ticket if it exists
            Optional<ETicket> ticketOpt = eTicketRepository.findByPaymentId(paymentId);
            if (ticketOpt.isPresent()) {
                ETicket ticket = ticketOpt.get();
                ticket.setTicketStatus("CANCELLED");
                eTicketRepository.save(ticket);
            }
            return updatedPayment;
        }
        return null;
    }
}