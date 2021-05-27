package com.oneirros.sally2.repository;

import com.oneirros.sally2.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<TripEntity, Long> {

    boolean existsByName(String name);
}
