package com.example.sharemate.item.memoryStorage;

import com.example.sharemate.item.dto.ItemCreateDto;
import com.example.sharemate.item.dto.ItemUpdateDto;
import com.example.sharemate.item.model.Item;

import java.util.List;

public interface ItemStorage {
    public Item create(Item item);

    public List<Item> getAll(Long userId);
    public Item getById(Long id);
    public List<Item> getByText(String text);

    public Item update(Item item);

    public void delete(Long id);
}
