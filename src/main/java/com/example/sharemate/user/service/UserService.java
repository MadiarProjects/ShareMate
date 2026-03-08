package com.example.sharemate.user.service;

import com.example.sharemate.exceptions.AlreadyExistException;
import com.example.sharemate.exceptions.NotFoundedException;
import com.example.sharemate.user.dto.UserCreateDto;
import com.example.sharemate.user.model.User;
import com.example.sharemate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User create(UserCreateDto userCreateDto) {
        User user = new User();
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        List<User> users= userRepository.findAll();
        if (users.contains(user)) {
            throw new AlreadyExistException("пользователь с таким email уже существует");
        } else {
            userRepository.save(user);
            return user;
        }
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User getById(Long id) {
        User user= userRepository.findById(id).orElse(null);
        if (user==null){
            throw new NotFoundedException("пользователя с таким айди не существует");
        }else {
            return user;
        }
    }
    @Transactional
    public User update(UserCreateDto userCreateDto, Long id) {
        User updatedUser = getById(id);
        List<User> users=getAll();
        boolean emailIsExists = users.stream()
                .anyMatch(user -> !(user.getId().equals(updatedUser.getId())) && user.getEmail().equals(userCreateDto.getEmail()));
        if (emailIsExists) {
            throw new AlreadyExistException("email already exists");
        }
        if (userCreateDto.getName() != null) {
            updatedUser.setName(userCreateDto.getName());
        }
        if (userCreateDto.getEmail() != null) {
            updatedUser.setEmail(userCreateDto.getEmail());
        }
        return userRepository.save(updatedUser);

    }
    @Transactional
    public void delete(Long id) {
        getById(id);
        userRepository.deleteById(id);
        log.info("user deleted at id:" + id);
    }
}
