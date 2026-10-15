package com.ticket_booking.app.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingRequest, Long> {
    List<BookingRequest> findByUserId(Long userId);
}