package com.example.sharemate.user.memoryStorage;

import com.example.sharemate.exceptions.AlreadyExictException;
import com.example.sharemate.exceptions.NotFoundedException;
import com.example.sharemate.item.Item;
import com.example.sharemate.user.dto.UserCreateDto;
import com.example.sharemate.user.dto.UserMapper;
import com.example.sharemate.user.dto.UserShortDto;
import com.example.sharemate.user.dto.UserUpdateDto;
import com.example.sharemate.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.id.uuid.LocalObjectUuidHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Slf4j
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private final List<User> users=new ArrayList<>();
    private final UserMapper userMapper;
    private Long nextId=0L;
    @Override
    public UserShortDto create(UserCreateDto userCreateDto) {
        User user = new User(
                ++nextId,
                userCreateDto.getLogin(),
                userCreateDto.getPassword(),
                userCreateDto.getItems()
        );
        if (users.contains(user)){
        throw new AlreadyExictException("пользователь с таким логином уже существует");
        }else {
            users.add(user);
            return userMapper.toShortDto(user);
        }
    }

    @Override
    public List<UserShortDto> getAll() {
        List<UserShortDto> userShortDtos=new ArrayList<>();
        users.forEach(user->{
            userShortDtos.add(userMapper.toShortDto(user));
        });
        return userShortDtos;
    }

    @Override
    public User getById(Long id) {
        return users.stream()
                .map(user -> {
                    user.getId().equals(id);
                })
                .findFirst()
                .orElseThrow(()->new NotFoundedException("пользователя с таким айди не существует"));
    }

    @Override
    public User update(UserUpdateDto userUpdateDto) {
        User user=getById(userUpdateDto.getId());
    }

    @Override
    public void delete(Long id) {

    }
}
