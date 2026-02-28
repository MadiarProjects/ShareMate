package com.example.sharemate.item.controller;

import com.example.sharemate.item.ItemService;
import com.example.sharemate.item.dto.ItemCreateDto;
import com.example.sharemate.item.dto.ItemUpdateDto;
import com.example.sharemate.item.module.Item;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.LifecycleState;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public Item create(@Valid @RequestBody ItemCreateDto itemCreateDto,@RequestHeader(name = "X-Sharer-User-Id")Long userId){
        return itemService.create(itemCreateDto,userId);
    }

    @GetMapping
    public List<Item> getALl(@RequestHeader(name = "X-Sharer-User-Id")Long userId){
        return itemService.getAll(userId);
    }

    @GetMapping("/{id}")
    public Item getById(@PathVariable Long id){
        return itemService.getById(id);
    }

    @GetMapping("/search")
    public List<Item> getByText(@RequestParam String text){
        return itemService.getByText(text);
    }

    @PatchMapping("/{id}")
    public Item update(@Valid @RequestBody ItemUpdateDto itemUpdateDto, @PathVariable Long id, @RequestHeader(name = "X-Sharer-User-Id")Long userId){
        return itemService.update(itemUpdateDto,id,userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,@RequestHeader(name = "X-Sharer-User-Id")Long userId){
        itemService.delete(id,userId);
    }
}
