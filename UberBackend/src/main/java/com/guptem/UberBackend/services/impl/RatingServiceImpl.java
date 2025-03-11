package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.RiderDto;
import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Rating;
import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.exceptions.ResourceNotFoundException;
import com.guptem.UberBackend.exceptions.RuntimeConflictException;
import com.guptem.UberBackend.repo.DriverRepo;
import com.guptem.UberBackend.repo.RatingRepo;
import com.guptem.UberBackend.repo.RiderRepo;
import com.guptem.UberBackend.services.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepo ratingRepo;
    private final DriverRepo driverRepo;
    private final RiderRepo riderRepo;
    private final ModelMapper modelMapper;

    public RatingServiceImpl(RatingRepo ratingRepo, DriverRepo driverRepo, RiderRepo riderRepo, ModelMapper modelMapper) {
        this.ratingRepo = ratingRepo;
        this.driverRepo = driverRepo;
        this.riderRepo = riderRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public DriverDto rateDriver(Ride ride, Integer rating) {

        Driver driver = ride.getDriver();

        Rating ratingObj = ratingRepo.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id: " + ride.getId()));

        if (ratingObj != null) {
            throw new RuntimeConflictException("Driver has already been rated!");
        }

        ratingObj.setDriverRating(rating);
        ratingRepo.save(ratingObj);

        Double newRating  = ratingRepo.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average()
                .orElse(0.0);

        driver.setRating(newRating);
        Driver savedDriver = driverRepo.save(driver);

        return modelMapper.map(savedDriver, DriverDto.class);

    }

    @Override
    public RiderDto rateRider(Ride ride, Integer rating) {

        Rider rider = ride.getRider();

        Rating ratingObj = ratingRepo.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found for ride with id: " + ride.getId()));

        if (ratingObj != null) {
            throw new RuntimeConflictException("Rider has already been rated!");
        }

        ratingObj.setRiderRating(rating);
        ratingRepo.save(ratingObj);

        Double newRating = ratingRepo.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getRiderRating)
                .average()
                .orElse(0.0);

        rider.setRating(newRating);
        Rider savedRider = riderRepo.save(rider);

        return modelMapper.map(savedRider, RiderDto.class);

    }

    @Override
    public void createNewRating(Ride ride) {

        Rating rating = Rating.builder()
                .rider(ride.getRider())
                .driver(ride.getDriver())
                .ride(ride)
                .build();

        ratingRepo.save(rating);
    }
}
