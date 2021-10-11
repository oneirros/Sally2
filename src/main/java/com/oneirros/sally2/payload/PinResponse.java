package com.oneirros.sally2.payload;

import com.oneirros.sally2.entity.PinEntity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class PinResponse {

    private final Long id;
    private final Long dayId;
    private final Long userId;
    private final String placeName;
    private final LocalTime arrivalTime;
    private final LocalTime departureTime;
    private final String notes;
    private final LocalDate createdOn;

    public static PinResponse of(PinEntity pin) {
        return new PinResponse(
                pin.getId(),
                pin.getDay().getId(),
                pin.getUser().getId(),
                pin.getPlaceName(),
                pin.getArrivalTime(),
                pin.getDepartureTime(),
                pin.getNotes(),
                pin.getCreatedOn()
        );
    }
}
