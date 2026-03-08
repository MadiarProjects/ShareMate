package com.example.sharemate.booking;


import com.example.sharemate.enums.ItemStatus;
import lombok.Data;

@Data
public class BookingResponseDto {
    private final Long userId;
    private final Long bookingId;
    private final ItemStatus status;
}
