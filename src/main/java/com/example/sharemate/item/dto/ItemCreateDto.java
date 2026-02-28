package com.example.sharemate.item.dto;

import com.example.sharemate.user.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemCreateDto {
    @NotBlank(message = "name should not be empty")
    private String name;
    @NotBlank(message = "description should not be empty")
    private String description;
    @NotNull(message = "available should not be null ")
    private Boolean available;
}
