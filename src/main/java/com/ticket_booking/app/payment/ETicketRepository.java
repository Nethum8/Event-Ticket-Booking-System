package com.ticket_booking.app.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ETicketRepository extends JpaRepository<ETicket, Long> {
    List<ETicket> findByUserId(Long userId);
    Optional<ETicket> findByTicketSerialCode(String ticketSerialCode);
    Optional<ETicket> findByPaymentId(Long paymentId);
}