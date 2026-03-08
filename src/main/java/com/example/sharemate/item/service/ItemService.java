package com.example.sharemate.item.service;

import com.example.sharemate.exceptions.AlreadyExistException;
import com.example.sharemate.exceptions.NotFoundedException;
import com.example.sharemate.item.dto.ItemCreateDto;
import com.example.sharemate.item.dto.ItemUpdateDto;
import com.example.sharemate.item.memoryStorage.ItemStorage;
import com.example.sharemate.item.model.Item;
import com.example.sharemate.item.repository.ItemRepository;
import com.example.sharemate.user.model.User;
import com.example.sharemate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Transactional
    public Item create(ItemCreateDto itemCreateDto, Long userId) {
        User user = userService.getById(userId);
        Item item = new Item();
        item.setName(itemCreateDto.getName());
        item.setDescription(itemCreateDto.getDescription());
        item.setOwner(user);
        item.setAvailable(itemCreateDto.getAvailable());

        List<Item> items = itemRepository.findAllByOwnerId(userId);

        boolean isItemExists = items.stream()
                .anyMatch(itemIsExists -> itemIsExists.getOwner().getId().equals(userId) && itemIsExists.getName().equals(item.getName()));
        if (isItemExists) {
            throw new AlreadyExistException("owner at id:" + userId + ", already has item with that name " + item.getName());
        } else {
            return itemRepository.save(item);
        }
    }

    public List<Item> getAll(Long userId) {
        userService.getById(userId);
        return itemRepository.findAllByOwnerId(userId);
    }

    @Transactional
    public Item getById(Long id) {
        Item item = itemRepository.findById(id).orElse(null);
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
}
