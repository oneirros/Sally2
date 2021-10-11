package com.oneirros.sally2.payload;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AddPinRequest {

    @NotNull
    private Long dayId;

    @NotNull
    private Long userId;

    @NotBlank
    @Length(min = 2, max = 50)
    private String placeName;

    @NotNull
    private LocalTime arrivalTime;

    @NotNull
    private LocalTime departureTime;

    @NotBlank
    @Length(min = 2, max = 250)
    private String notes;
}
