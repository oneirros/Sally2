package com.oneirros.sally2.service;

import com.oneirros.sally2.entity.DayEntity;
import com.oneirros.sally2.entity.TripEntity;
import com.oneirros.sally2.entity.UserEntity;
import com.oneirros.sally2.payload.AddDayRequest;
import com.oneirros.sally2.payload.ChangeDayDateRequest;
import com.oneirros.sally2.payload.DayResponse;
import com.oneirros.sally2.repository.DayRepository;
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
public class DayService {

    private final DayRepository dayRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    @Autowired
    public DayService(DayRepository dayRepository, TripRepository tripRepository,
                      UserRepository userRepository) {

        this.dayRepository = dayRepository;
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }

    public void addDay(AddDayRequest request) throws NotFoundException, IllegalArgumentException {

        boolean exist = dayRepository.existsByDateAndTripId(request.getDate(),request.getTripId());
        if(exist) {
            throw new IllegalArgumentException("You already have plan for this day");
        }

        TripEntity trip = tripRepository.findById(request.getTripId()).orElseThrow(
                () -> new NotFoundException(String.format("Not found trip ID = %d", request.getTripId()))
        );

        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new NotFoundException(String.format("Not found user ID = %d", request.getUserId()))
        );

        DayEntity day = DayEntity.builder()
                .trip(trip)
                .user(user)
                .date(request.getDate())
                .createdOn(LocalDate.now())
                .build();

        dayRepository.save(day);

        System.out.println("Wykonałem się !!!!!!");
    }

    public DayResponse getDay(Long dayId) throws NotFoundException {

        DayEntity day = dayRepository.findById(dayId).orElseThrow(
                () -> new NotFoundException(String.format("Not found day ID = %d", dayId))
        );

        return DayResponse.of(day);
    }

    public void updateDate(Long dayId, ChangeDayDateRequest request) throws NotFoundException {

        DayEntity day = dayRepository.findById(dayId).orElseThrow(
                () -> new NotFoundException(String.format("Not found day ID = %d", dayId))
        );

        day.setDate(request.getDate());
        dayRepository.save(day);
    }

    public void deleteDay(Long dayId) throws NotFoundException {

        DayEntity day = dayRepository.findById(dayId).orElseThrow(
                () -> new NotFoundException(String.format("Not found day ID = %d", dayId))
        );

        dayRepository.deleteById(dayId);
    }

    public Page<DayResponse> getAllDaysForTrip(Long tripId, Integer page, Integer size) throws NotFoundException {

        TripEntity trip = tripRepository.findById(tripId).orElseThrow(
                () -> new NotFoundException(String.format("Not found trip ID = %d", tripId))
        );

        Pageable daysPage = PageRequest.of(page, size);

        List<DayResponse> daysList = dayRepository.findDaysByTrip(trip, daysPage)
                .stream()
                .map(DayResponse::of)
                .collect(Collectors.toList());

        return new PageImpl<>(daysList);
    }
}
