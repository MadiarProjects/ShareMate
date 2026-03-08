package com.example.sharemate.booking;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;


    @PostMapping
    public Booking create(@Valid @RequestBody BookingCreateDto bookingCreateDto, @RequestHeader("X-Sharer-User-Id") Long userId){
        return bookingService.create(bookingCreateDto,userId);
    }

    @GetMapping
    public List<Booking> getAll(){
        return bookingService.getAll();
    }
    @GetMapping("/{bookingId}")
    public Booking getBookingsByBookerId(@PathVariable Long bookingId,@RequestHeader(name = "X-Sharer-User-Id")Long bookerId){
        return bookingService.getBookingByBookerId(bookingId,bookerId);
    }
    @GetMapping("/owner")
    public List<Booking> getAllByBookerId(@RequestHeader(name = "X-Sharer-User-Id")Long bookerId){
        return bookingService.getAllByBookerId(bookerId);
    }

    @PatchMapping("/{bookingId}")
    public Booking approve(@RequestHeader(name = "X-Sharer-User-Id")Long userId,@PathVariable Long bookingId,@RequestParam(name = "approved")boolean approved){
        return bookingService.approve(userId,bookingId,approved);
    }


}
