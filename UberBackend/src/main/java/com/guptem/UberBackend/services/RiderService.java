package com.guptem.UberBackend.services;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.RideDto;
import com.guptem.UberBackend.dto.RideRequestDto;
import com.guptem.UberBackend.dto.RiderDto;
import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);
    RideDto cancelRide(Long rideId);
    DriverDto rateDriver(Long rideId, Integer rating);
    RiderDto getMyProfile();
    Page<RideDto> getAllMyRides(PageRequest pageRequest);
    Rider createNewRider(User user);
    Rider getCurrentRider();

}
