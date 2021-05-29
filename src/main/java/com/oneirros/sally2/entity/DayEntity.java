package com.oneirros.sally2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@Table(name = "days")
@AllArgsConstructor
@NoArgsConstructor
public class DayEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private TripEntity trip;

    @ManyToOne
    private UserEntity user;
    private LocalDate date;
    private LocalDate createdOn;
}
