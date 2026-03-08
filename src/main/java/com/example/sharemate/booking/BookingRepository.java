package com.example.sharemate.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    Booking findByIdAndBooker_Id(Long id, Long bookerId);

    List<Booking> findAllByBooker_Id(Long bookerId);
}
