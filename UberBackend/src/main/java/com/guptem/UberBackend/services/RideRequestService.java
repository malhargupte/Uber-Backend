package com.guptem.UberBackend.services;

import com.guptem.UberBackend.entities.RideRequest;

public interface RideRequestService {

    RideRequest findRideRequestById(Long rideRequestId);
    void update (RideRequest rideRequest);
}
