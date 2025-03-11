package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.RideDto;
import com.guptem.UberBackend.dto.RideRequestDto;
import com.guptem.UberBackend.dto.RiderDto;
import com.guptem.UberBackend.entities.*;
import com.guptem.UberBackend.entities.enums.RideRequestStatus;
import com.guptem.UberBackend.entities.enums.RideStatus;
import com.guptem.UberBackend.exceptions.ResourceNotFoundException;
import com.guptem.UberBackend.repo.RideRequestRepo;
import com.guptem.UberBackend.repo.RiderRepo;
import com.guptem.UberBackend.services.DriverService;
import com.guptem.UberBackend.services.RatingService;
import com.guptem.UberBackend.services.RideService;
import com.guptem.UberBackend.services.RiderService;
import com.guptem.UberBackend.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepo rideRequestRepo;
    private final RiderRepo riderRepo;
    private final RideService rideService;
//    private final RiderService riderService;
    private final DriverService driverService;
    private final RatingService ratingService;

//    public RiderServiceImpl(ModelMapper modelMapper, RideFareCalculationStrategy rideFareCalculationStrategy, DriverMatchingStrategy driverMatchingStrategy, RideRequestRepo rideRequestRepo, RiderRepo riderRepo) {
//        this.modelMapper = modelMapper;
//        this.rideFareCalculationStrategy = rideFareCalculationStrategy;
//        this.driverMatchingStrategy = driverMatchingStrategy;
//        this.rideRequestRepo = rideRequestRepo;
//        this.riderRepo = riderRepo;
//    }

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        Rider rider = getCurrentRider();

        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(rider);

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepo.save(rideRequest);

        List<Driver> drivers =  rideStrategyManager
                .driverMatchingStrategy(rider.getRating())
                .findMatchingDriver(rideRequest);

        //TODO : Send notifications to all the drivers about the ride request

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {

        Rider rider = getCurrentRider();

        Ride ride = rideService.getRideById(rideId);
        if(!rider.equals(ride.getDriver())) {
            throw new RuntimeException("Rider does not own this ride with id: " + rideId);
        }

        Ride savedRide =  rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        driverService.updateDriverAvailability(ride.getDriver(), true);

        return modelMapper.map(savedRide, RideDto.class);

    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {

        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if (!rider.equals(ride.getRider())) {
            throw new RuntimeException("Rider cannot rate the driver!");
        }

        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("Ride has not yet ended, hence driver cannot be rated!");
        }

        return ratingService.rateDriver(ride, rating);

    }

    @Override
    public RiderDto getMyProfile() {

        Rider rider = getCurrentRider();
        return modelMapper.map(rider, RiderDto.class);

    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {

        Rider currentRider = getCurrentRider();

        return rideService.getAllRidesOfRider(currentRider, pageRequest)
                .map(
                        ride -> modelMapper.map(ride, RideDto.class)
                );

    }

    @Override
    public Rider createNewRider(User user) {

        Rider rider = Rider
                .builder()
                .user(user)
                .rating(0.0)
                .build();

        return riderRepo.save(rider);

    }

    @Override
    public Rider getCurrentRider() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Rider not associated with user with id: " + user.getId()));
    }
}
