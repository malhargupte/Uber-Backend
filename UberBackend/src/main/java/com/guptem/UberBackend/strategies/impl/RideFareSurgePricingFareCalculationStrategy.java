package com.guptem.UberBackend.strategies.impl;

import com.guptem.UberBackend.entities.RideRequest;
import com.guptem.UberBackend.services.DistanceService;
import com.guptem.UberBackend.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    private final static double SURGE_FACTOR = 2;

    public RideFareSurgePricingFareCalculationStrategy(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @Override
    public double calculateFare(RideRequest rideRequest) {

        double distance = distanceService.calculateDistance(
                rideRequest.getPickUpLocation(),
                rideRequest.getDropOffLocation()
        );

        return distance * RIDE_FARE_MULTIPLIER * SURGE_FACTOR;

    }
}
