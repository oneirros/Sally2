package com.oneirros.sally2.controller;

import com.oneirros.sally2.payload.AddTripRequest;
import com.oneirros.sally2.payload.ChangeTripNameRequest;
import com.oneirros.sally2.payload.TripResponse;
import com.oneirros.sally2.service.TripService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/trip")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {

        this.tripService = tripService;
    }

    @PostMapping
    public void addTrip(@RequestBody @Valid AddTripRequest request) {

        try {
            tripService.addTrip(request);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
        }

    }

    @GetMapping("/{tripId}")
    public TripResponse getTrip(@PathVariable("tripId") Long tripId) {

        try {
            return tripService.getTrip(tripId);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/{tripId}/name")
    public void updateTripName(@PathVariable("tripId") Long tripId,
                               @RequestBody @Valid ChangeTripNameRequest request) {

        try {
            tripService.updateTripName(tripId, request);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
        }
    }

    @DeleteMapping("/{tripId}")
    public void deleteTrip(@PathVariable("tripId") Long tripId) {

        try {
            tripService.deleteTrip(tripId);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
