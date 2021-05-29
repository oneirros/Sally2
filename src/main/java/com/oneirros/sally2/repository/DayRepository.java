package com.oneirros.sally2.repository;

import com.oneirros.sally2.entity.DayEntity;
import com.oneirros.sally2.entity.TripEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;

public interface DayRepository extends JpaRepository<DayEntity, Long> {

    boolean existsByDateAndTripId(LocalDate date, Long tripId);
    List<DayEntity> findDaysByTrip(TripEntity trip, Pageable page);
}
