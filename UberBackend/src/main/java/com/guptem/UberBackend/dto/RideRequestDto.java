package com.guptem.UberBackend.dto;

import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.entities.enums.PaymentMethods;
import com.guptem.UberBackend.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDto {

    private Long id;

    private PointDto pickUpLocation;
    private PointDto dropOffLocation;

    private LocalDateTime requestedTime;

    private RiderDto rider;

    private PaymentMethods paymentMethods;

    private RideRequestStatus rideRequestStatus;
    private Double fare;


}
