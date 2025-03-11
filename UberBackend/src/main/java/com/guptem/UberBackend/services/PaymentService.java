package com.guptem.UberBackend.services;

import com.guptem.UberBackend.entities.Payment;
import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);
    Payment createNewPayment(Ride ride);
    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);

}
