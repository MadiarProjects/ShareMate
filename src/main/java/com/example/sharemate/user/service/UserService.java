package com.example.sharemate.user.service;

import com.example.sharemate.user.dto.UserCreateDto;
import com.example.sharemate.user.memoryStorage.UserStorage;
import com.example.sharemate.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User create(UserCreateDto userCreateDto) {
        return userStorage.create(userCreateDto);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User getById(Long id){
        return userStorage.getById(id);
    }

    public User update(UserCreateDto userCreateDto,Long id){
        return userStorage.update(userCreateDto,id);
    }

    public void delete(Long id){
        userStorage.delete(id);
    }
}
