package com.example.sharemate.user.memoryStorage;

import com.example.sharemate.user.model.User;
import com.example.sharemate.user.dto.UserCreateDto;
import com.example.sharemate.user.dto.UserShortDto;
import com.example.sharemate.user.dto.UserUpdateDto;

import java.util.List;

public interface UserStorage {
    public UserShortDto create(UserCreateDto userCreateDto);

    public List<UserShortDto> getAll();
    public User getById(Long id);

    public User update(UserUpdateDto userUpdateDto);

    public void delete(Long id);
}
