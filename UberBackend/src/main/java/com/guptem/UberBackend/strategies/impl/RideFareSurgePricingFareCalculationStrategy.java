package com.guptem.UberBackend.strategies.impl;

import com.guptem.UberBackend.entities.RideRequest;
import com.guptem.UberBackend.strategies.RideFareCalculationStrategy;
import org.springframework.stereotype.Service;

@Service
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    @Override
    public double calculateFare(RideRequest rideRequestDto) {
        return 0;
    }
}
