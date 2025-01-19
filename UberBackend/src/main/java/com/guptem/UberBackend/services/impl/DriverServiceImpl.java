package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.DriverDto;
import com.guptem.UberBackend.dto.RideDto;
import com.guptem.UberBackend.dto.RiderDto;
import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.RideRequest;
import com.guptem.UberBackend.entities.enums.RideRequestStatus;
import com.guptem.UberBackend.entities.enums.RideStatus;
import com.guptem.UberBackend.exceptions.ResourceNotFoundException;
import com.guptem.UberBackend.repo.DriverRepo;
import com.guptem.UberBackend.services.DriverService;
import com.guptem.UberBackend.services.PaymentService;
import com.guptem.UberBackend.services.RideRequestService;
import com.guptem.UberBackend.services.RideService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public DriverServiceImpl(RideRequestService rideRequestService, DriverRepo driverRepo, RideService rideService, ModelMapper modelMapper, PaymentService paymentService) {
        this.rideRequestService = rideRequestService;
        this.driverRepo = driverRepo;
        this.rideService = rideService;
        this.modelMapper = modelMapper;
        this.paymentService = paymentService;
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
        return null;
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

        return driverRepo.findById(3L)
                .orElseThrow(() -> new ResourceNotFoundException("Driver could not be found!"));

    }

    @Override
    public Driver updateDriverAvailability(Driver driver, boolean available) {

        driver.setAvailable(available);
        return driverRepo.save(driver);

    }
}
