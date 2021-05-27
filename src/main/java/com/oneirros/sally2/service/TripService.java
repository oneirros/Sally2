package com.oneirros.sally2.service;

import com.oneirros.sally2.entity.TripEntity;
import com.oneirros.sally2.entity.UserEntity;
import com.oneirros.sally2.payload.AddTripRequest;
import com.oneirros.sally2.payload.ChangeTripNameRequest;
import com.oneirros.sally2.payload.TripResponse;
import com.oneirros.sally2.repository.TripRepository;
import com.oneirros.sally2.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    @Autowired
    public TripService(TripRepository tripRepository,
                       UserRepository userRepository) {

        this.tripRepository = tripRepository;
        this.userRepository = userRepository;

    }

    public void addTrip(AddTripRequest request)
            throws NotFoundException,
            IllegalArgumentException {

        boolean exist = tripRepository.existsByName(request.getName());
        if (exist) {
            throw new IllegalArgumentException(String.format("Trip %s already exist", request.getName()));
        }

        Long userId = request.getUserId();
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("Not found user ID = %d", request.getUserId()))
        );

        TripEntity trip = TripEntity.builder()
                .user(user)
                .name(request.getName())
                .createdOn(LocalDate.now())
                .build();

        tripRepository.save(trip);
    }

    public TripResponse getTrip(Long tripId) throws NotFoundException {

        TripEntity trip = tripRepository.findById(tripId).orElseThrow(
                () -> new NotFoundException(String.format("Not found trip ID = %d", tripId))
        );

        return TripResponse.of(trip);
    }

    public void updateTripName(Long tripId, ChangeTripNameRequest request)
            throws NotFoundException,
            IllegalArgumentException {

        TripEntity trip = tripRepository.findById(tripId).orElseThrow(
                () -> new NotFoundException(String.format("Not found trip ID = %d", tripId))
        );

        boolean exist = tripRepository.existsByName(request.getName());
        if (exist) {
            throw new IllegalArgumentException(String.format("Trip %s already exist", request.getName()));
        }
        else {
            trip.setName(request.getName());
            tripRepository.save(trip);
        }
    }

    public void deleteTrip(Long tripId) throws NotFoundException {

        TripEntity trip = tripRepository.findById(tripId).orElseThrow(
                () -> new NotFoundException(String.format("Not found trip ID = %d", tripId))
        );

        tripRepository.deleteById(tripId);
    }
}
