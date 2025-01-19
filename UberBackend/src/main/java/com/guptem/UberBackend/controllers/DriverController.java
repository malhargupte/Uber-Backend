package com.guptem.UberBackend.controllers;

import com.guptem.UberBackend.dto.RideDto;
import com.guptem.UberBackend.dto.RideStartDto;
import com.guptem.UberBackend.services.DriverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping(path = "/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId) {

        return ResponseEntity.ok(driverService.acceptRide(rideRequestId));

    }

    @PostMapping(path = "/startRide/{rideRequestId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideRequestId,
                                             @RequestBody RideStartDto rideStartDto) {

        return ResponseEntity.ok(driverService.startRide(rideRequestId, rideStartDto.getOtp()));

    }

    @PostMapping(path = "/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId) {

        return ResponseEntity.ok(driverService.endRide(rideId));

    }

}
