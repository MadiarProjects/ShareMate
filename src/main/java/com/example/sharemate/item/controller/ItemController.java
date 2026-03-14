package com.example.sharemate.item.controller;

import com.example.sharemate.comment.Comment;
import com.example.sharemate.item.dto.ItemWithCommentsDto;
import com.example.sharemate.item.service.ItemService;
import com.example.sharemate.item.dto.ItemCreateDto;
import com.example.sharemate.item.dto.ItemUpdateDto;
import com.example.sharemate.item.model.Item;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/{itemId}/comment")
    public Comment createComment(@PathVariable Long itemId,@RequestHeader(name = "X-Sharer-User-Id") Long userId,@RequestBody String text){
        return itemService.createComment(itemId,userId,text);
    }
    @GetMapping("/{itemId}")
    public ItemWithCommentsDto getItemWithComments(@PathVariable Long itemId,@RequestHeader(name = "X-Sharer-User-Id") Long userId){
        return itemService.getItemWithComments(itemId,userId);
    }

    @GetMapping
    public List<Item> getALl(@RequestHeader(name = "X-Sharer-User-Id")Long userId){
        return itemService.getAll(userId);
    }
//
//    @GetMapping("/{itemId}")
//    public Item getById(@PathVariable Long itemId){
//        return itemService.getById(itemId);
//    }

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
