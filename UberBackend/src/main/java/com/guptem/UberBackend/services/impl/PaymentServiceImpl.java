package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.entities.Payment;
import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.enums.PaymentStatus;
import com.guptem.UberBackend.exceptions.ResourceNotFoundException;
import com.guptem.UberBackend.repo.PaymentRepo;
import com.guptem.UberBackend.services.PaymentService;
import com.guptem.UberBackend.strategies.PaymentStrategyManager;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepo paymentRepo;
    private final PaymentStrategyManager paymentStrategyManager;

    public PaymentServiceImpl(PaymentRepo paymentRepo, PaymentStrategyManager paymentStrategyManager) {
        this.paymentRepo = paymentRepo;
        this.paymentStrategyManager = paymentStrategyManager;
    }

    @Override
    public void processPayment(Ride ride) {

        Payment payment = paymentRepo.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for ride with id: " + ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethods()).processPayment(payment);

    }

    @Override
    public Payment createNewPayment(Ride ride) {

        Payment payment = Payment.builder()
                .ride(ride)
                .paymentMethods(ride.getPaymentMethods())
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        return paymentRepo.save(payment);

    }

    @Override
    public void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus) {

        payment.setPaymentStatus(paymentStatus);
        paymentRepo.save(payment);

    }
}
