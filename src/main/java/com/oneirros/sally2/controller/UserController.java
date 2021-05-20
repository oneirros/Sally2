package com.oneirros.sally2.controller;

import com.oneirros.sally2.exception.SamePasswordException;
import com.oneirros.sally2.payload.AddUserRequest;
import com.oneirros.sally2.payload.ChangeUserPasswordRequest;
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

    @GetMapping("/{userID}")
    public UserResponse getUser(@PathVariable("userID") Long userID) {
        try {
            return userService.getUser(userID);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public void addUser(@RequestBody @Valid AddUserRequest request) {
        try {
            userService.addUser(request);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
        }
    }

    @PatchMapping("/{userID}/password")
    public void updateUserPassword(@PathVariable("userID") Long userID,
                                   @RequestBody @Valid ChangeUserPasswordRequest request
                                   ) {

        try {
            userService.updateUserPassword(userID, request);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (SamePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }


    }

    @DeleteMapping("/{userID}")
    public void deleteUser(@PathVariable("userID") Long userID) {

        try {
            userService.deleteUser(userID);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }



}
