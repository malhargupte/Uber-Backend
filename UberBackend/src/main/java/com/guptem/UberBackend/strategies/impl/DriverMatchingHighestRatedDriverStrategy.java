package com.guptem.UberBackend.strategies.impl;

//import com.guptem.UberBackend.dto.RideRequestDto;
import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.RideRequest;
import com.guptem.UberBackend.repo.DriverRepo;
import com.guptem.UberBackend.strategies.DriverMatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepo driverRepo;

    public DriverMatchingHighestRatedDriverStrategy(DriverRepo driverRepo) {
        this.driverRepo = driverRepo;
    }

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return driverRepo.findTenNearByTopRatedDrivers(rideRequest.getPickUpLocation());
    }
}
