package com.ticket_booking.app.payment;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "etickets")
public class ETicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @Column(unique = true, nullable = false)
    private String ticketSerialCode; // Unique code for QR/Verification

    private Long paymentId;
    private Long bookingId;
    private Long userId;
    private Long eventId;
    private String seatNumbers; // e.g., "VIP-A1, VIP-A2"

    // Status: "ISSUED", "USED", "CANCELLED"
    private String ticketStatus;

    private LocalDateTime issuedAt;

    // Default Constructor (Required by JPA)
    public ETicket() {
        this.ticketSerialCode = "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.ticketStatus = "ISSUED";
        this.issuedAt = LocalDateTime.now();
    }

    // Parameterized Constructor
    public ETicket(Long paymentId, Long bookingId, Long userId, Long eventId, String seatNumbers) {
        this.ticketSerialCode = "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.eventId = eventId;
        this.seatNumbers = seatNumbers;
        this.ticketStatus = "ISSUED";
        this.issuedAt = LocalDateTime.now();
    }

    // Getters and Setters (OOP Encapsulation)
    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }

    public String getTicketSerialCode() { return ticketSerialCode; }
    public void setTicketSerialCode(String ticketSerialCode) { this.ticketSerialCode = ticketSerialCode; }

    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(String seatNumbers) { this.seatNumbers = seatNumbers; }

    public String getTicketStatus() { return ticketStatus; }
    public void setTicketStatus(String ticketStatus) { this.ticketStatus = ticketStatus; }

    public LocalDateTime getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDateTime issuedAt) { this.issuedAt = issuedAt; }
}