package com.oneirros.sally2.repository;

import com.oneirros.sally2.entity.DayEntity;
import com.oneirros.sally2.entity.PinEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PinRepository extends JpaRepository<PinEntity, Long> {

    boolean existsByPlaceName(String placeName);
    List<PinEntity> findPinsByDay(DayEntity day, Pageable page);
}
