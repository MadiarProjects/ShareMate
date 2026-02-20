package com.example.sharemate.user.dto;

import com.example.sharemate.item.Item;
import lombok.Data;

import java.util.List;

@Data
public class UserUpdateDto {
    private Long id;
    private List<Item> items;
}
