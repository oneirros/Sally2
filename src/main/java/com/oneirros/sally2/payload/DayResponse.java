package com.oneirros.sally2.payload;

import com.oneirros.sally2.entity.DayEntity;
import com.oneirros.sally2.entity.TripEntity;
import com.oneirros.sally2.entity.UserEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DayResponse {

    private final Long id;
    private final Long tripId;
    private final Long userId;
    private final LocalDate date;
    private final LocalDate createdOn;

    public static DayResponse of(DayEntity day) {

        return new DayResponse(
                day.getId(),
                day.getTrip().getId(),
                day.getUser().getId(),
                day.getDate(),
                day.getCreatedOn()
        );
    }
}
