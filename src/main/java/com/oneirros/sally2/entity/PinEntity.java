package com.oneirros.sally2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@Builder
@Table(name = "pins")
@AllArgsConstructor
@NoArgsConstructor
public class PinEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private DayEntity day;

    @ManyToOne
    private UserEntity user;

    private String placeName;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private String notes;
    private LocalDate createdOn;

}
