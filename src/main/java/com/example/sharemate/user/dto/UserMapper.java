package com.example.sharemate.user.dto;

import com.example.sharemate.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserShortDto toShortDto(User user){
        return new UserShortDto(user.getId(),user.getLogin());
    }
}
