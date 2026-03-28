package com.example.sharemate.item;

import java.util.List;

public interface ItemStorage {
    public Item create(Item item);

    public List<Item> getAll(Long userId);
    public Item getById(Long id);
    public List<Item> getByText(String text);

    public Item update(Item item);

    public void delete(Long id);
}
