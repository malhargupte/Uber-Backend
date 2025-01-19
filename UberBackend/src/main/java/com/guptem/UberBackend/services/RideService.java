package com.guptem.UberBackend.services;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.RideRequestDto;
import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.RideRequest;
import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.entities.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getRideById(Long id);
//    void matchWithDrivers(RideRequestDto rideRequestDto);
    Ride createNewRide(RideRequest rideRequest, Driver driver);
    Ride updateRideStatus(Ride ride, RideStatus rideStatus);
    Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest);
    Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest);

}
