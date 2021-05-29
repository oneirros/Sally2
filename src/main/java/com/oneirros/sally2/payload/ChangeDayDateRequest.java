package com.oneirros.sally2.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class ChangeDayDateRequest {

    @NotNull
    private LocalDate date;
}
