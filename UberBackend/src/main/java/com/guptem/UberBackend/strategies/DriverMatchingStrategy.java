package com.guptem.UberBackend.strategies;

import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {

    List<Driver> findMatchingDriver(RideRequest rideRequest);

}
