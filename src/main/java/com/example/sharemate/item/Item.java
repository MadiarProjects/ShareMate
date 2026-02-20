package com.example.sharemate.item;

import com.example.sharemate.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private Long id;
    private String name;
    private String description;
    private User owner;
    private Boolean isAvailable;

}
