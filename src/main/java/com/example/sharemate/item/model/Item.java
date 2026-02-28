package com.example.sharemate.item.model;

import com.example.sharemate.user.model.User;
import lombok.*;

@Setter@Getter
public class Item {
    private Long id;
    private String name;
    private String description;
    private User owner;
    private Boolean available;

    public Item(String name, String description, User owner, Boolean available) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.available = available;
    }

}
