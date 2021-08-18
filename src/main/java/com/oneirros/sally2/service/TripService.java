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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new NotFoundException(String.format("Not found user ID = %d", request.getUserId()))
        );

        boolean exist = tripRepository.existsByUserAndName(user, request.getName());
        if (exist) {
            throw new IllegalArgumentException(String.format("Trip %s already exist", request.getName()));
        }

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

    public Page<TripResponse> getAllTripsForUser(Long userId, Integer page, Integer size) throws NotFoundException {

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format("Not found user ID = %d", userId))
        );

        Pageable tripsPage = PageRequest.of(page, size);

        List<TripResponse> tripsList = tripRepository.findTripsByUser(user, tripsPage)
                .stream()
                .map(TripResponse::of)
                .collect(Collectors.toList());

        return new PageImpl<>(tripsList);
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
