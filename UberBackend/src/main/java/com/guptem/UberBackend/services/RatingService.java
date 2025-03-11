package com.guptem.UberBackend.services;


import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.RiderDto;
import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.Rider;

public interface RatingService {

    DriverDto rateDriver(Ride ride, Integer rating);
    RiderDto rateRider(Ride ride, Integer rating);
    void createNewRating(Ride ride);

}
