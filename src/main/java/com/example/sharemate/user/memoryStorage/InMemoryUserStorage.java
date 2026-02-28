package com.example.sharemate.user.memoryStorage;

import com.example.sharemate.exceptions.AlreadyExistException;
import com.example.sharemate.exceptions.NotFoundedException;
import com.example.sharemate.user.dto.UserCreateDto;
//import com.example.sharemate.user.dto.UserMapper;
import com.example.sharemate.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final List<User> users = new ArrayList<>();
    private Long nextId = 0L;

    @Override
    public User create(UserCreateDto userCreateDto) {
        User user = new User(
                userCreateDto.getName(),
                userCreateDto.getEmail()
        );
        if (users.contains(user)) {
            throw new AlreadyExistException("пользователь с таким email уже существует");
        } else {
            user.setId(++nextId);
            users.add(user);
            return user;

        }
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
                .orElseThrow(() -> new NotFoundedException("пользователя с таким айди не существует"));
    }

    @Override
    public User update(UserCreateDto userCreateDto, Long id) {
        User updatedUser = getById(id);
        boolean emailIsExists = users.stream()
                .anyMatch(user ->!(user.getId().equals(updatedUser.getId())) &&user.getEmail().equals(userCreateDto.getEmail()));
        if (emailIsExists) {
            throw new AlreadyExistException("email already exists");
        }
        if (userCreateDto.getName() != null) {
            updatedUser.setName(userCreateDto.getName());
        }
        if (userCreateDto.getEmail() != null) {
            updatedUser.setEmail(userCreateDto.getEmail());
        }
        users.removeIf(user->user.getId().equals(id));
        users.add(updatedUser);
        return updatedUser;
    }

    @Override
    public void delete(Long id) {
        if (users.removeIf(user -> user.getId().equals(id))) {
            log.info("user deleted at id:"+id);
        }else {
            throw new NotFoundedException("user with this id doesnt exist");
        }
    }
}
