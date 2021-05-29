package com.oneirros.sally2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AddDayRequest {

    @NotNull
    private Long tripId;

    @NotNull
    private Long userId;

    @NotNull
    private LocalDate date;

}
