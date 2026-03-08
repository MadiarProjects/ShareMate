package com.example.sharemate.booking;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class BookingCreateDto {
    private LocalDateTime start;
    private LocalDateTime end;
    private Long itemId;
}
