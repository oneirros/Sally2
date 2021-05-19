package com.oneirros.sally2.controller;

import com.oneirros.sally2.payload.AddUserRequest;
import com.oneirros.sally2.payload.UserResponse;
import com.oneirros.sally2.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable("userId") Long userId) {
        try {
            return userService.getUser(userId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public void addUser(@RequestBody @Valid AddUserRequest userRequest) {
        try {
            userService.addUser(userRequest);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
        }
    }

}
