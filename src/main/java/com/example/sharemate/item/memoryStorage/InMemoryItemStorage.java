package com.example.sharemate.item.memoryStorage;

import com.example.sharemate.exceptions.AlreadyExistException;
import com.example.sharemate.exceptions.NotFoundedException;
import com.example.sharemate.item.dto.ItemCreateDto;
import com.example.sharemate.item.dto.ItemUpdateDto;
import com.example.sharemate.item.model.Item;
import com.example.sharemate.user.memoryStorage.UserStorage;
import com.example.sharemate.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InMemoryItemStorage implements ItemStorage{
    private final List<Item> items = new ArrayList<>();

    private Long nextId = 0L;

    @Override
    public Item create(Item item) {
        item.setId(++nextId);
        items.add(item);
        return item;
    }

    @Override
    public List<Item> getAll(Long userId) {
        return items.stream()
                .filter(item -> item.getOwner().getId().equals(userId))
                .toList();
    }

    @Override
    public Item getById(Long id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Item> getByText(String text){
        return items.stream()
                .filter(item -> item.getDescription().toLowerCase().contains(text.toLowerCase())&&item.getAvailable()==true)
                .toList();
    }

    @Override
    public Item update(Item item) {

        items.removeIf(i->i.getId().equals(item.getId()));
        items.add(item);
        return item;
    }

    @Override
    public void delete(Long id) {
        items.removeIf(item -> item.getId().equals(id));

    }

}
