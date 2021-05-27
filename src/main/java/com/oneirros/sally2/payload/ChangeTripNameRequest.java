package com.oneirros.sally2.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class ChangeTripNameRequest {

    @NotBlank
    @Length(min = 2, max = 30)
    private String name;
}
