package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.entities.RideRequest;
import com.guptem.UberBackend.exceptions.ResourceNotFoundException;
import com.guptem.UberBackend.repo.RideRequestRepo;
import com.guptem.UberBackend.services.RideRequestService;
import org.springframework.stereotype.Service;

@Service
public class RideRequestServiceImpl implements RideRequestService  {

    private final RideRequestRepo rideRequestRepo;

    public RideRequestServiceImpl(RideRequestRepo rideRequestRepo) {
        this.rideRequestRepo = rideRequestRepo;
    }

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        return rideRequestRepo.findById(rideRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride request not found!"));
    }

    @Override
    public void update(RideRequest rideRequest) {

        rideRequestRepo.findById(rideRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ride Request could not be found"));
        rideRequestRepo.save(rideRequest);

    }

}
