package com.example.sharemate.user;

import com.example.sharemate.user.User;

import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final List<User> users = new ArrayList<>();
    private Long nextId = 0L;

    @Override
    public User create(User user) {
        user.setId(++nextId);
        users.add(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public User getById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User update(User user) {

        users.removeIf(u -> u.getId().equals(user.getId()));
        users.add(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}
