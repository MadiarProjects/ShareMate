package com.example.sharemate.comment;


import com.example.sharemate.booking.Booking;
import com.example.sharemate.booking.BookingService;
import com.example.sharemate.enums.ItemStatus;
import com.example.sharemate.exceptions.NotFoundedException;
import com.example.sharemate.item.model.Item;
import com.example.sharemate.user.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;


    public Comment createComment(Item item, User user,String text,List<Booking> bookings){

        boolean isUsedAndApproved=bookings.stream()
                .anyMatch(booking -> booking.getItem().equals(item)&&booking.getStatus().equals(ItemStatus.APPROVED));
        if(!isUsedAndApproved){
            throw  new NotFoundedException("this user at id:"+user.getId()+" did not use item at id:"+item.getId());
        }
        Comment comment=new Comment();
        comment.setText(text);
        comment.setItem(item);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByItemId(Long itemId){
        return commentRepository.findAllByItem_Id(itemId);
    }
}
