package com.guptem.UberBackend.strategies;

import com.guptem.UberBackend.entities.RideRequest;

public interface RideFareCalculationStrategy {

    double RIDE_FARE_MULTIPLIER = 10; //can be used in env var
    double calculateFare(RideRequest rideRequest);

}
