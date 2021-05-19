package com.oneirros.sally2.payload;

import com.oneirros.sally2.entity.UserDetails;
import com.oneirros.sally2.entity.UserEntity;
import lombok.Data;

@Data
public class UserResponse {

    private final long id;
    private final UserDetails userDetails;
    private final String email;
    private final String login;

    public static UserResponse of(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getUserDetails(),
                user.getEmail(),
                user.getLogin()
        );
    }

}
