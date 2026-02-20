package com.example.sharemate.user.dto;

import com.example.sharemate.item.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


import java.util.List;
@Data
public class UserCreateDto {
    @NotBlank(message = "логин не может быть пустым")
    private String login;
    @Length(min = 4, message = "пароль должен быть длиннее 4 символов")
    private String password;
    private List<Item>items;
}
