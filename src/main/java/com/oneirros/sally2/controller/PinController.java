package com.oneirros.sally2.controller;

import com.oneirros.sally2.exception.ArrivalTimeaAfterDepartureTimeExpeciont;
import com.oneirros.sally2.payload.AddPinRequest;
import com.oneirros.sally2.payload.ChangePinDataRequest;
import com.oneirros.sally2.payload.PinResponse;
import com.oneirros.sally2.service.PinService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/pin")
public class PinController {

    private final PinService pinService;

    @Autowired
    public PinController(PinService pinService) {
        this.pinService = pinService;
    }

    @PostMapping
    public void addPin(@RequestBody @Valid AddPinRequest addPinRequest) {

        try {
            pinService.addPin(addPinRequest);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, e.getMessage());
        }
        catch (ArrivalTimeaAfterDepartureTimeExpeciont e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }

    }

    @GetMapping("/{pinId}")
    public PinResponse getPin(@PathVariable("pinId") Long pinId) {
        try {
            return pinService.getPin(pinId);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{pinId}")
    public void deletePin(@PathVariable("pinId") Long pinId) {

        try {
            this.pinService.deletePin(pinId);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/allPins/{dayId}")
    public Page<PinResponse> getAllPinsForDay(@PathVariable("dayId") Long dayId,
                                              @RequestParam(defaultValue = "0") Integer page,
                                              @RequestParam(defaultValue = "2") Integer size) {
        try {
            return this.pinService.getAllPinForDays(dayId, page, size);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PatchMapping("/{pinId}")
    public void updatePin(@PathVariable("pinId") Long pinId, @RequestBody @Valid ChangePinDataRequest request) {
        try {
            this.pinService.updatePin(pinId, request);
        }
        catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
