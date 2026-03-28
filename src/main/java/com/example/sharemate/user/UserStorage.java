package com.example.sharemate.user;

import com.example.sharemate.user.User;

import java.util.List;
public interface UserStorage {
    public User create(User user);

    public List<User> getAll();
    public User getById(Long id);

    public User update(User user);

    public void delete(Long id);
}
