package com.guptem.UberBackend.services;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.RideDto;
import com.guptem.UberBackend.dto.RiderDto;

import java.util.List;

public interface DriverService {

    RiderDto acceptRide(Long rideId);
    RiderDto cancelRide(Long rideId);
    RiderDto startRide(Long rideId);
    RiderDto endRide(Long rideId);
    RiderDto rateRider(Long rideId, Integer rating);
    DriverDto getMyProfile();
    List<RideDto> getAllMyRides();

}
