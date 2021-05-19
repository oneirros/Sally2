package com.oneirros.sally2.service;

import com.oneirros.sally2.entity.UserDetails;
import com.oneirros.sally2.entity.UserEntity;
import com.oneirros.sally2.payload.AddUserRequest;
import com.oneirros.sally2.payload.UserResponse;
import com.oneirros.sally2.repository.UserRepository;
import javassist.NotFoundException;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    public final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUser(@NonNull Long userId) throws NotFoundException {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("Not found user ID = " + userId)
        );

        return UserResponse.of(userEntity);
    }

    public void addUser(AddUserRequest userRequest) throws IllegalArgumentException{

        boolean exist = userRepository.existsByEmail(userRequest.getEmail());
        if (exist) {
            throw new IllegalArgumentException("Email " + userRequest.getEmail() + " taken");
        }

        UserDetails details = UserDetails.builder()
                .name(userRequest.getUserDetailsRequest().getName())
                .surname(userRequest.getUserDetailsRequest().getSurname())
                .phone(userRequest.getUserDetailsRequest().getPhone())
                .build();

        UserEntity user = UserEntity.builder()
                .userDetails(details)
                .role(userRequest.getRole())
                .email(userRequest.getEmail())
                .login(userRequest.getLogin())
                .password(userRequest.getPassword())
                .createdOn(LocalDate.now())
                .build();

        userRepository.save(user);
    }
}
