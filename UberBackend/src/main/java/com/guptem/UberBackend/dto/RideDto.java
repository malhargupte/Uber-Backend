package com.guptem.UberBackend.dto;

import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.entities.enums.PaymentMethods;
import com.guptem.UberBackend.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {

    private Long id;

    private Point pickUpLocation;
    private Point dropOffLocation;

    private LocalDateTime createdTime;

    private RiderDto rider;
    private DriverDto driver;

    private PaymentMethods paymentMethods;

    private RideStatus rideStatus;
    private String otp;
    private Double fare;

    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

}
