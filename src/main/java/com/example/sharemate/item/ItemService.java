package com.example.sharemate.item;

import com.example.sharemate.booking.Booking;
import com.example.sharemate.booking.BookingRepository;
import com.example.sharemate.comment.Comment;
import com.example.sharemate.comment.CommentService;
import com.example.sharemate.exceptions.AlreadyExistException;
import com.example.sharemate.exceptions.NotFoundedException;
import com.example.sharemate.request.RequestRepository;
import com.example.sharemate.request.RequestService;
import com.example.sharemate.user.User;
import com.example.sharemate.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final CommentService commentService;
    private final BookingRepository bookingRepository;
    private final RequestService requestService;
    private final RequestRepository requestRepository;

    @Transactional
    public Item create(ItemCreateDto itemCreateDto, Long userId) {
        User user = userService.getById(userId);
        Item item = new Item();
        item.setName(itemCreateDto.getName());
        item.setDescription(itemCreateDto.getDescription());
        item.setOwner(user);
        item.setAvailable(itemCreateDto.isAvailable());

        List<Item> items = itemRepository.findAllByOwnerId(userId);

        boolean isItemExists = items.stream()
                .anyMatch(itemIsExists -> itemIsExists.getOwner().getId().equals(userId) && itemIsExists.getName().equals(item.getName()));
        if (isItemExists) {
            throw new AlreadyExistException("owner at id:" + userId + ", already has item with that name " + item.getName());
        } else {
            if (itemCreateDto.getRequestId()!=null&&itemCreateDto.isAvailable()){
                item.setRequest(requestRepository.findById(itemCreateDto.getRequestId())
                        .orElseThrow(()->new NotFoundedException("not founded request")));
                requestService.addItemToRequest(item);
            }
            return itemRepository.save(item);
        }
    }


    @Transactional
    public Comment createComment(Long itemId, Long userId, String text) {
        Item item = getById(itemId);
        User user = userService.getById(userId);
        List<Booking> bookings= bookingRepository.findAllByBooker_Id(user.getId());
        return commentService.createComment(item, user, text,bookings);
    }
    @Transactional
    public ItemWithCommentsDto getItemWithComments(Long itemId,Long userId){
        Item item=getById(itemId);
        User user = userService.getById(userId);
        List<Comment> comments=commentService.getCommentsByItemId(itemId).stream()
                .filter(comment -> comment.getUser().equals(user))
                .toList();
        return new ItemWithCommentsDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getOwner(),
                item.isAvailable(),
                comments
        );
    }

    public List<Item> getAll(Long userId,Integer from,Integer size) {
        Pageable pageable = PageRequest.of(
                from / size,
                size,
                Sort.by( "id")
        );
        userService.getById(userId);
        return itemRepository.findAllByOwnerId(userId,pageable);
    }

    @Transactional
    public Item getById(Long itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            throw new NotFoundedException("item с таким айди не существует");
        } else {
            return item;
        }
    }


    public List<Item> getByText(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        } else {
            return itemRepository.findAllByDescription(text);
        }
    }

    @Transactional
    public Item update(ItemUpdateDto itemUpdateDto, Long id, Long userId) {
        userService.getById(userId);
        Item updatedItem = getById(id);
        List<Item> items = getAll(userId);
        boolean itemIsExists = items.stream()
                .anyMatch(item -> !(item.getId().equals(id)) && item.getName().equals(itemUpdateDto.getName()));
        if (itemIsExists) {
            throw new AlreadyExistException("cannot update item name to already exists item name that user has ");
        }
        if (itemUpdateDto.getName() != null) {
            updatedItem.setName(itemUpdateDto.getName());
        }
        if (itemUpdateDto.getDescription() != null) {
            updatedItem.setDescription(itemUpdateDto.getDescription());
        }
        if (itemUpdateDto.getAvailable() != null) {
            updatedItem.setAvailable(itemUpdateDto.getAvailable());
        }
        return itemRepository.save(updatedItem);
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Item item = getById(id);
        if (!(item.getOwner().getId().equals(userId))) {
            throw new NotFoundedException("this owner at id:" + userId + " ,does not have item at this id:" + id);
        }
        itemRepository.deleteById(id);
        log.info("item deleted at id:" + id + ",owner id:" + userId);
    }

    private List<Item> getAll(Long userId) {
        userService.getById(userId);
        return itemRepository.findAllByOwnerId(userId);
    }
}
