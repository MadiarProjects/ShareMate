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
@RequiredArgsConstructor
public class InMemoryItemStorage implements ItemStorage{
    private final List<Item> items = new ArrayList<>();
    private final UserStorage userStorage;
    //    private final UserMapper userMapper;
    private Long nextId = 0L;

    @Override
    public Item create(ItemCreateDto itemCreateDto,Long userId) {
        User user=userStorage.getById(userId);
        Item item = new Item(
                itemCreateDto.getName(),
                itemCreateDto.getDescription(),
                user,
                itemCreateDto.getAvailable()
        );
        boolean isItemExists=items.stream()
                .anyMatch(itemIsExists ->itemIsExists.getOwner().getId().equals(userId)&&itemIsExists.getName().equals(item.getName()));
        if (isItemExists) {
            throw new AlreadyExistException("owner at id:"+userId+", already has item with that name "+item.getName());
        } else {
            item.setId(++nextId);
            items.add(item);
            return item;
        }
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
                .orElseThrow(() -> new NotFoundedException("item с таким айди не существует"));
    }

    @Override
    public List<Item> getByText(String text){
        if (text== null || text.isBlank()){
            return new ArrayList<>();
        }
        return items.stream()
                .filter(item -> item.getDescription().toLowerCase().contains(text.toLowerCase())&&item.getAvailable()==true)
                .toList();
    }

    @Override
    public Item update(ItemUpdateDto itemUpdateDto, Long id, Long userId) {

        Item updatedItem = getByIdAndUserId(id,userId);
        boolean itemIsExists = items.stream()
                .anyMatch(item ->item.getOwner().getId().equals(userId)&&!(item.getId().equals(id))&&item.getName().equals(itemUpdateDto.getName()));
        if (itemIsExists) {
            throw new AlreadyExistException("this owner already has that item");
        }
        if (itemUpdateDto.getName() != null) {
            updatedItem.setName(itemUpdateDto.getName());
        }
        if (itemUpdateDto.getDescription() != null) {
            updatedItem.setDescription(itemUpdateDto.getDescription());
        }
        if (itemUpdateDto.getAvailable()!=null){
            updatedItem.setAvailable(itemUpdateDto.getAvailable());
        }
        items.removeIf(item->item.getId().equals(id));
        items.add(updatedItem);
        return updatedItem;
    }

    @Override
    public void delete(Long id,Long userId) {
        if (items.removeIf(item -> item.getId().equals(id)&&item.getOwner().getId().equals(userId))) {
            log.info("item deleted at id:"+id+",owner id:"+userId);
        }else {
            throw new NotFoundedException("this owner at id:"+userId+" ,does not have item with this id:"+id);
        }
    }


    private Item getByIdAndUserId(Long id, Long userId){
        return items.stream()
                .filter(item -> item.getId().equals(id)&&item.getOwner().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new NotFoundedException("item с таким айди не существует"));
    }
}
