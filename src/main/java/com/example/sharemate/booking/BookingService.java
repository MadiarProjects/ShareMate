package com.example.sharemate.booking;


import com.example.sharemate.enums.ItemStatus;
import com.example.sharemate.exceptions.NotFoundedException;
import com.example.sharemate.item.model.Item;
import com.example.sharemate.item.service.ItemService;
import com.example.sharemate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService  {
    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final UserService userService;

    public Booking create(BookingCreateDto bookingCreateDto, Long userId) {
        Booking booking = new Booking();
        booking.setStart(bookingCreateDto.getStart());
        booking.setEnd(bookingCreateDto.getEnd());
        Item item = itemService.getById(bookingCreateDto.getItemId());
        if (!item.getAvailable()) {
            throw new InvalidParameterException("item can not be booked");
        }
        booking.setItem(item);
        booking.setBooker(userService.getById(userId));
        booking.setStatus(ItemStatus.WAITING);

        return bookingRepository.save(booking);
    }

    public List<Booking> getAll(){
        return bookingRepository.findAll();
    }
    public Booking getBookingByBookerId(Long bookingId,Long bookerId){
        return bookingRepository.findByIdAndBooker_Id(bookingId,bookerId);
    }

    public List<Booking> getAllByBookerId(Long bookerId){
        return bookingRepository.findAllByBooker_Id(bookerId);
    }

    public Booking approve(Long userId, Long bookingId, boolean approve) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking == null) {
            throw new NotFoundedException("booking does not exist");
        }
        if (!booking.getItem().getOwner().getId().equals(userId)) {
            throw new InvalidParameterException("user does not have permission to approve");
        }
        if (approve) {
            booking.setStatus(ItemStatus.APPROVED);
        } else {
            booking.setStatus(ItemStatus.REJECTED);
        }
        return bookingRepository.save(booking);

    }

//    public List<Booking> findAll() {
//        return bookingRepository.findAll();
//    }
//
//    public List<Booking> getById(Long id) {
//
//    }
}
