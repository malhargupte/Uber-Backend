package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.RideDto;
import com.guptem.UberBackend.dto.RiderDto;
import com.guptem.UberBackend.entities.*;
import com.guptem.UberBackend.entities.enums.RideRequestStatus;
import com.guptem.UberBackend.entities.enums.RideStatus;
import com.guptem.UberBackend.exceptions.ResourceNotFoundException;
import com.guptem.UberBackend.repo.DriverRepo;
import com.guptem.UberBackend.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepo driverRepo;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    public DriverServiceImpl(RideRequestService rideRequestService, DriverRepo driverRepo, RideService rideService, ModelMapper modelMapper, PaymentService paymentService, RatingService ratingService) {
        this.rideRequestService = rideRequestService;
        this.driverRepo = driverRepo;
        this.rideService = rideService;
        this.modelMapper = modelMapper;
        this.paymentService = paymentService;
        this.ratingService = ratingService;
    }

    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {

        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)) {
            throw new RuntimeException("Ride request could not be accepted, it is " + rideRequest.getRideRequestStatus());
        }

        Driver currentDriver = getCurrentDriver();

        if(!currentDriver.isAvailable()) {
            throw new RuntimeException("Driver is not available!");
        }

//        currentDriver.setAvailable(false);
//        Driver savedDriver = driverRepo.save(currentDriver);
        Driver savedDriver = updateDriverAvailability(currentDriver, false);

        Ride ride = rideService.createNewRide(rideRequest, savedDriver);
        return modelMapper.map(ride, RideDto.class);

    }

    @Override
    public RideDto startRide(Long rideId, String otp) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            throw new ResourceNotFoundException("Driver cannot accept this ride as he did not accept it earlier!");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride Status is not confirmed, hence cannot be started!");
        }

        if (!otp.equals(ride.getOtp())) {
            throw new RuntimeException("Invalid OTP!");
        }

        ride.setStartedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);

        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);

        return modelMapper.map(savedRide, RideDto.class);

    }

    @Override
    public RideDto cancelRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);

        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot cancel this ride!");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)) {
            throw new RuntimeException("Ride cannot be cancelled, invalid status: " + ride.getRideStatus());
        }

        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
//        driver.setAvailable(true);
//        driverRepo.save(driver);
        updateDriverAvailability(driver, true);

        return modelMapper.map(ride, RideDto.class);

    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver cannot cancel this ride!");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING)) {
            throw new RuntimeException("Ride status is not ONGOING, cannot be ended!");
        }

        ride.setEndedAt(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(ride, RideStatus.ENDED);
        updateDriverAvailability(driver,true);

        paymentService.processPayment(ride);

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if (!driver.equals(ride.getDriver())) {
            throw new RuntimeException("Driver is not the owner of this ride!");
        }

        if (!ride.getRideStatus().equals(RideStatus.ENDED)) {
            throw new RuntimeException("You cannot rate the rider, the ride has not ended yet!");
        }

        return ratingService.rateRider(ride, rating);

    }

    @Override
    public DriverDto getMyProfile() {

        Driver currentDriver = getCurrentDriver();
        return modelMapper.map(currentDriver, DriverDto.class);

    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {

        Driver currentDriver = getCurrentDriver();

        return rideService.getAllRidesOfDriver(currentDriver, pageRequest)
                .map(
                        ride -> modelMapper.map(ride, RideDto.class)
                );

    }

    @Override
    public Driver getCurrentDriver() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not associated with user with id " + user.getId()));

    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {

        driver.setAvailable(available);
        return driverRepo.save(driver);

    }

    @Override
    public Driver createNewDriver(Driver driver) {

        return driverRepo.save(driver);

    }
}
