package com.guptem.UberBackend.strategies;

import com.guptem.UberBackend.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.guptem.UberBackend.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.guptem.UberBackend.strategies.impl.RideFareDefaultFareCalculationStrategy;
import com.guptem.UberBackend.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy nearestDriverStrategy;
    private final RideFareDefaultFareCalculationStrategy defaultFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy;

    public RideStrategyManager(DriverMatchingHighestRatedDriverStrategy highestRatedDriverStrategy, DriverMatchingNearestDriverStrategy nearestDriverStrategy, RideFareDefaultFareCalculationStrategy defaultFareCalculationStrategy, RideFareSurgePricingFareCalculationStrategy surgePricingFareCalculationStrategy) {
        this.highestRatedDriverStrategy = highestRatedDriverStrategy;
        this.nearestDriverStrategy = nearestDriverStrategy;
        this.defaultFareCalculationStrategy = defaultFareCalculationStrategy;
        this.surgePricingFareCalculationStrategy = surgePricingFareCalculationStrategy;
    }

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating) {

        if(riderRating >= 4.8) {
            return highestRatedDriverStrategy;
        } else {
            return nearestDriverStrategy;
        }

    }

    public RideFareCalculationStrategy rideFareCalculationStrategy() {

        //6PM to 9PM
        LocalTime surgeStartTime = LocalTime.of(18, 0);
        LocalTime surgeEndTime = LocalTime.of(21, 0);
        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);
        if(isSurgeTime) {
            return surgePricingFareCalculationStrategy;
        } else {
            return defaultFareCalculationStrategy;
        }

    }

}
