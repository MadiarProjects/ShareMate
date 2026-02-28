package com.example.sharemate.item.memoryStorage;

import com.example.sharemate.item.dto.ItemCreateDto;
import com.example.sharemate.item.dto.ItemUpdateDto;
import com.example.sharemate.item.module.Item;
import com.example.sharemate.user.dto.UserCreateDto;
import com.example.sharemate.user.model.User;

import java.util.List;

public interface ItemStorage {
    public Item create(ItemCreateDto itemCreateDto,Long userId);

    public List<Item> getAll(Long userId);
    public Item getById(Long id);
    public List<Item> getByText(String text);

    public Item update(ItemUpdateDto itemUpdateDto, Long id, Long userId);

    public void delete(Long id,Long userId);
}
