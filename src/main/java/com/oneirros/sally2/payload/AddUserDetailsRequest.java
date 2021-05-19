package com.oneirros.sally2.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class AddUserDetailsRequest {

    @NotBlank
    @Length(min = 2, max = 20)
    private String name;

    @NotBlank
    @Length(min = 2, max = 30)
    private String surname;

    @Length(min = 3, max = 20) //TODO Size of phone
    private String phone;
}
