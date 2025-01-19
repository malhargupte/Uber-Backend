package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.RideRequestDto;
import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.RideRequest;
import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.entities.enums.RideRequestStatus;
import com.guptem.UberBackend.entities.enums.RideStatus;
import com.guptem.UberBackend.exceptions.ResourceNotFoundException;
import com.guptem.UberBackend.repo.RideRepo;
import com.guptem.UberBackend.services.RideRequestService;
import com.guptem.UberBackend.services.RideService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RideServiceImpl implements RideService {

    private final RideRepo rideRepo;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;

    public RideServiceImpl(RideRepo rideRepo, RideRequestService rideRequestService, ModelMapper modelMapper) {
        this.rideRepo = rideRepo;
        this.rideRequestService = rideRequestService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Ride getRideById(Long id) {
        return rideRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ride could not be found with this ID"));
    }

//    @Override
//    public void matchWithDrivers(RideRequestDto rideRequestDto) {
//
//    }

    @Override
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {

        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        Ride ride = modelMapper.map(rideRequest, Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateRandomOTP());
        ride.setId(null);

        rideRequestService.update(rideRequest);
        return rideRepo.save(ride);

    }

    @Override
    public Ride updateRideStatus(Ride ride, RideStatus rideStatus) {
        ride.setRideStatus(rideStatus);
        return rideRepo.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {

        return rideRepo.findByRider(rider, pageRequest);

    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {

        return rideRepo.findByDriver(driver, pageRequest);
    }

    private String generateRandomOTP() {

        Random random = new Random();
        int otpInt = random.nextInt(10000); //0 to 9999
        return String.format("%04d", otpInt);

    }
}
