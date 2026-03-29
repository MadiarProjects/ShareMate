package com.example.sharemate.booking;


import com.example.sharemate.enums.ItemStatus;
import com.example.sharemate.exceptions.NotFoundedException;
import com.example.sharemate.item.Item;
import com.example.sharemate.item.ItemService;
import com.example.sharemate.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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

    public List<Booking> getAllByBookerId(Long bookerId,Integer from,Integer size){
        Pageable pageable = PageRequest.of(
                from / size,
                size,
                Sort.by( "id")
        );
        return bookingRepository.findAllByBooker_Id(bookerId,pageable);
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

    public List<Booking> findAllByOwnerId(Long ownerId,Integer from,Integer size ) {
        Pageable pageable = PageRequest.of(
                from / size,
                size,
                Sort.by( "id")
        );
        List<Booking> isNullOrNot= bookingRepository.findAllByItem_Owner_Id(ownerId);
        if(isNullOrNot.isEmpty()){
            throw new NotFoundedException("not founded any match");
        }
        Page<Booking> bookingPage=new PageImpl<>(isNullOrNot,pageable,isNullOrNot.size());
        return bookingPage.getContent();
    }
}
