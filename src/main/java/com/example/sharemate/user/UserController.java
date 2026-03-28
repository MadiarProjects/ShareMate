package com.example.sharemate.user;

import com.example.sharemate.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.sharemate.user.User;

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
    public List<User> getAll(
            @RequestParam (defaultValue = "0",required = false)Integer from,
            @RequestParam(defaultValue = "20",required = false) Integer size
    ){
        return userService.getAll(from,size);
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
