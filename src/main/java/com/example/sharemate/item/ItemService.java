package com.example.sharemate.item;

import com.example.sharemate.item.dto.ItemCreateDto;
import com.example.sharemate.item.dto.ItemUpdateDto;
import com.example.sharemate.item.memoryStorage.ItemStorage;
import com.example.sharemate.item.model.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;

    public Item create(ItemCreateDto itemCreateDto,Long id){
        return itemStorage.create(itemCreateDto,id);
    }

    public List<Item> getAll(Long userId){
        return itemStorage.getAll(userId);
    }

    public Item getById(Long id){
        return itemStorage.getById(id);
    }

    public List<Item> getByText(String text){
        return itemStorage.getByText(text);
    }

    public Item update(ItemUpdateDto itemUpdateDto, Long id, Long userId){
        return itemStorage.update(itemUpdateDto,id,userId);
    }

    public void delete(Long id,Long userId){
        itemStorage.delete(id,userId);
    }
}
