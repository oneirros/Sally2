package com.oneirros.sally2.service;

import com.oneirros.sally2.entity.DayEntity;
import com.oneirros.sally2.entity.PinEntity;
import com.oneirros.sally2.entity.UserEntity;
import com.oneirros.sally2.exception.ArrivalTimeaAfterDepartureTimeExpeciont;
import com.oneirros.sally2.payload.AddPinRequest;
import com.oneirros.sally2.payload.ChangePinDataRequest;
import com.oneirros.sally2.payload.DayResponse;
import com.oneirros.sally2.payload.PinResponse;
import com.oneirros.sally2.repository.DayRepository;
import com.oneirros.sally2.repository.PinRepository;
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
public class PinService {

    private final PinRepository pinRepository;
    private final UserRepository userRepository;
    private final DayRepository dayRepository;

    @Autowired
    public PinService(PinRepository pinRepository, UserRepository userRepository, DayRepository dayRepository){
        this.pinRepository = pinRepository;
        this.userRepository = userRepository;
        this.dayRepository = dayRepository;
    }

    public void addPin(AddPinRequest request) throws IllegalArgumentException,
                                                     NotFoundException,
                                                     ArrivalTimeaAfterDepartureTimeExpeciont
            {

        boolean existPlace = this.pinRepository.existsByPlaceName(request.getPlaceName());

        if (request.getArrivalTime().isAfter(request.getDepartureTime())) {
            throw new ArrivalTimeaAfterDepartureTimeExpeciont("Arrivale Time can't be after Depature Time");
        }
        else if (existPlace) {
            throw new IllegalArgumentException(
                    String.format("You already have pin's name: %s", request.getPlaceName())
            );
        }

        UserEntity user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new NotFoundException(String.format("Not found user ID = %d", request.getUserId()))
        );

        DayEntity day = dayRepository.findById(request.getDayId()).orElseThrow(
                () -> new NotFoundException(String.format("Not found day ID = %d", request.getDayId()))
        );

        PinEntity pin = PinEntity.builder()
                .day(day)
                .user(user)
                .placeName(request.getPlaceName())
                .arrivalTime(request.getArrivalTime())
                .departureTime(request.getDepartureTime())
                .notes(request.getNotes())
                .createdOn(LocalDate.now())
                .build();

        pinRepository.save(pin);

    }

    public PinResponse getPin(Long pinId) throws NotFoundException {

        PinEntity pin = this.pinRepository.findById(pinId).orElseThrow(
                () -> new NotFoundException(String.format("Not found Pin ID = %d", pinId))
        );

        return PinResponse.of(pin);
    }

    public void deletePin(Long pinId) throws NotFoundException {

        PinEntity pin = this.pinRepository.findById(pinId).orElseThrow(
                () -> new NotFoundException(String.format("Not found pin ID = %d", pinId))
        );

        pinRepository.deleteById(pinId);
    }

    public Page<PinResponse> getAllPinForDays(Long dayId, Integer page, Integer size) throws NotFoundException {

        DayEntity day = this.dayRepository.findById(dayId).orElseThrow(
                () -> new NotFoundException(String.format("Not found day ID = %d", dayId))
        );

        Pageable pinsPage = PageRequest.of(page, size);

        List<PinResponse> pinsList = this.pinRepository.findPinsByDay(day, pinsPage)
                .stream()
                .map(PinResponse::of)
                .collect(Collectors.toList());

        return new PageImpl<>(pinsList);
    }

    public void updatePin(Long pinId, ChangePinDataRequest request) throws NotFoundException {

        PinEntity pin = this.pinRepository.findById(pinId).orElseThrow(
                () -> new NotFoundException(String.format("Not found pin ID = %d", pinId))
        );

        pin.setPlaceName(request.getPlaceName());
        pin.setArrivalTime(request.getArrivalTime());
        pin.setDepartureTime(request.getDepartureTime());
        pin.setNotes(request.getNotes());

        pinRepository.save(pin);
    }
}
