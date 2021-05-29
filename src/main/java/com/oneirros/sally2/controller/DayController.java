package com.oneirros.sally2.controller;

import com.oneirros.sally2.payload.AddDayRequest;
import com.oneirros.sally2.payload.ChangeDayDateRequest;
import com.oneirros.sally2.payload.DayResponse;
import com.oneirros.sally2.service.DayService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/day")
public class DayController {

    private final DayService dayService;

    @Autowired
    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @PostMapping
    public void addDay(@RequestBody @Valid AddDayRequest request) {

        try {
            dayService.addDay(request);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
        }
    }

    @GetMapping("/{dayId}")
    public DayResponse getDay(@PathVariable("dayId") Long dayId) {

        try {
            return dayService.getDay(dayId);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/allDays/{tripId}")
    public Page<DayResponse> getAllDaysForTrip(@PathVariable("tripId") Long tripId,
                                               @RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "2") Integer size) {
        try {
            return dayService.getAllDaysForTrip(tripId, page, size);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/{dayId}")
    public void updateDate(@PathVariable("dayId") Long dayId, @RequestBody @Valid ChangeDayDateRequest request) {

        try {
            dayService.updateDate(dayId, request);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{dayId}")
    public void deleteDay(@PathVariable("dayId") Long dayId) {

        try {
            dayService.deleteDay(dayId);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
