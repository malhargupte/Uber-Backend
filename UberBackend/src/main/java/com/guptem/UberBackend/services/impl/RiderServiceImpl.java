package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.RideDto;
import com.guptem.UberBackend.dto.RideRequestDto;
import com.guptem.UberBackend.dto.RiderDto;
import com.guptem.UberBackend.entities.RideRequest;
import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.entities.User;
import com.guptem.UberBackend.entities.enums.RideRequestStatus;
import com.guptem.UberBackend.repo.RideRequestRepo;
import com.guptem.UberBackend.repo.RiderRepo;
import com.guptem.UberBackend.services.RiderService;
import com.guptem.UberBackend.strategies.DriverMatchingStrategy;
import com.guptem.UberBackend.strategies.RideFareCalculationStrategy;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideFareCalculationStrategy rideFareCalculationStrategy;
    private final DriverMatchingStrategy driverMatchingStrategy;
    private final RideRequestRepo rideRequestRepo;
    private final RiderRepo riderRepo;

    public RiderServiceImpl(ModelMapper modelMapper, RideFareCalculationStrategy rideFareCalculationStrategy, DriverMatchingStrategy driverMatchingStrategy, RideRequestRepo rideRequestRepo, RiderRepo riderRepo) {
        this.modelMapper = modelMapper;
        this.rideFareCalculationStrategy = rideFareCalculationStrategy;
        this.driverMatchingStrategy = driverMatchingStrategy;
        this.rideRequestRepo = rideRequestRepo;
        this.riderRepo = riderRepo;
    }

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {
        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);

        Double fare = rideFareCalculationStrategy.calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest savedRideRequest = rideRequestRepo.save(rideRequest);

        driverMatchingStrategy.findMatchingDriver(rideRequest);

        return modelMapper.map(savedRideRequest, RideRequestDto.class);
    }

    @Override
    public RiderDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
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
}
