package com.example.sharemate.user.memoryStorage;

import com.example.sharemate.user.model.User;
import com.example.sharemate.user.dto.UserCreateDto;

import java.util.List;
public interface UserStorage {
    public User create(UserCreateDto userCreateDto);

    public List<User> getAll();
    public User getById(Long id);

    public User update(UserCreateDto userCreateDto,Long id);

    public void delete(Long id);
}
