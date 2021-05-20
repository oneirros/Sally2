package com.oneirros.sally2.payload;

import com.oneirros.sally2.entity.UserRole;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AddUserRequest {

    @NotNull
    private AddUserDetailsRequest userDetailsRequest;

    @NotNull
    private UserRole role;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 3, max = 20)
    private String login;

    @NotBlank
    @Length(min = 8, max = 20)
    private String password;

}
