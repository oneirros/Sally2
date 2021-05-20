package com.oneirros.sally2.service;

import com.oneirros.sally2.entity.UserDetails;
import com.oneirros.sally2.entity.UserEntity;
import com.oneirros.sally2.exception.SamePasswordException;
import com.oneirros.sally2.payload.AddUserRequest;
import com.oneirros.sally2.payload.ChangeUserPasswordRequest;
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
                () -> new NotFoundException(String.format("Not found user ID = %d", userId))
        );

        return UserResponse.of(userEntity);
    }

    public void addUser(AddUserRequest userRequest) throws IllegalArgumentException{

        boolean exist = userRepository.existsByEmail(userRequest.getEmail());
        if (exist) {
            throw new IllegalArgumentException(String.format("Email %s taken", userRequest.getEmail()));
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

    public void updateUserPassword(Long userID, ChangeUserPasswordRequest request)
            throws NotFoundException, SamePasswordException {

        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException(String.format("Not found user of ID = %d", userID)));

        if (!request.getPassword().equals(user.getPassword())) {
            user.setPassword(request.getPassword());
            userRepository.save(user);
        } else {
            throw new SamePasswordException("Password is the same as the previous");
        }

    }

    public void deleteUser(Long userID) throws NotFoundException {

        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new NotFoundException(String.format("Not found user of ID = %d", userID)));

        userRepository.deleteById(userID);
    }
}
