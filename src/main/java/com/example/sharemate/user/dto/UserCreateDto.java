package com.example.sharemate.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreateDto {
    @NotBlank(message = "имя не может быть пустым")
    private String name;
    @NotBlank(message = "эмеил не может быть пустым")
    @Email
    private String email;

}
