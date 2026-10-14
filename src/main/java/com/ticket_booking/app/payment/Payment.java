package com.ticket_booking.app.payment;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long bookingId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private double amount;

    // Methods: "CREDIT_CARD", "DEBIT_CARD", "BANK_TRANSFER"
    private String paymentMethod;

    // Unique transaction reference number
    @Column(unique = true)
    private String transactionReference;

    // Status: "COMPLETED", "FAILED", "REFUNDED"
    private String paymentStatus;

    private LocalDateTime paymentDate;

    // Default Constructor (Required by JPA)
    public Payment() {
        this.paymentDate = LocalDateTime.now();
        this.transactionReference = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.paymentStatus = "COMPLETED";
    }

    // Parameterized Constructor
    public Payment(Long bookingId, Long userId, double amount, String paymentMethod) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionReference = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.paymentStatus = "COMPLETED";
        this.paymentDate = LocalDateTime.now();
    }

    // Getters and Setters (OOP Encapsulation)
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}