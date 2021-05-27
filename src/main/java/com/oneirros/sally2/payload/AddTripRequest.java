package com.oneirros.sally2.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddTripRequest {

    @NotNull
    private Long userId;

    @NotBlank
    @Length(min = 2, max = 30)
    private String name;
}
