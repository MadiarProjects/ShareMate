package com.example.sharemate.user.controller;

import com.example.sharemate.user.dto.UserCreateDto;
import com.example.sharemate.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sharemate.user.model.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping
    public User create(@Valid @RequestBody UserCreateDto userCreateDto){
        return userService.create(userCreateDto);
    }
    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }
    @GetMapping("/{userId}")
    public User getById(@PathVariable(name = "userId") Long id){
        return userService.getById(id);
    }
    @PatchMapping("/{userId}")
    public User update(@RequestBody UserCreateDto userCreateDto,@PathVariable(name = "userId") Long id){
        return userService.update(userCreateDto,id);
    }
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable(name = "userId") Long id){
        userService.delete(id);
    }
}
