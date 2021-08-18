package com.oneirros.sally2.repository;

import com.oneirros.sally2.entity.TripEntity;
import com.oneirros.sally2.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<TripEntity, Long> {

    boolean existsByName(String name);
    boolean existsByUserAndName(UserEntity user, String name);
    List<TripEntity> findTripsByUser(UserEntity user, Pageable page);
}
