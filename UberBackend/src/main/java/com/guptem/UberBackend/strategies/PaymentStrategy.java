package com.guptem.UberBackend.strategies;

import com.guptem.UberBackend.entities.Payment;

public interface PaymentStrategy {

    Double PLATFORM_COMMISSION = 0.3;
    void processPayment(Payment payment);

}
