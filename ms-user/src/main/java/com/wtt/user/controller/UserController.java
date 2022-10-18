package com.wtt.user.controller;

import com.wtt.commondependencies.dto.ResponseDto;
import com.wtt.user.dto.CreateUserRequest;
import com.wtt.user.dto.UserDto;
import com.wtt.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseDto createUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest);
    }

    @GetMapping("/{id}")
    public ResponseDto getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public ResponseDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/all")
    public ResponseDto getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/all/managers")
    public ResponseDto getAllManagers() {
        return userService.getAllManagers();
    }

    @GetMapping("/all/employees")
    public ResponseDto getAllEmployees() {
        return userService.getAllEmployees();
    }
}
