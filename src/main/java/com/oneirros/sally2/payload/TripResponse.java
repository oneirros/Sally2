package com.oneirros.sally2.payload;

import com.oneirros.sally2.entity.TripEntity;
import com.oneirros.sally2.entity.UserEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TripResponse {

    private final Long id;
    private final UserEntity user;
    private final String name;
    private final LocalDate createdOn;

    public static TripResponse of(TripEntity trip) {

        return new TripResponse(
                trip.getId(),
                trip.getUser(),
                trip.getName(),
                trip.getCreatedOn()
        );
    }
}
