package com.guptem.UberBackend.strategies.impl;

import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Payment;
import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.entities.enums.PaymentStatus;
import com.guptem.UberBackend.entities.enums.TransactionMethod;
import com.guptem.UberBackend.repo.PaymentRepo;
import com.guptem.UberBackend.services.PaymentService;
import com.guptem.UberBackend.services.WalletService;
import com.guptem.UberBackend.strategies.PaymentStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Rider has 232 Rs in his wallet
//Ride fare is 100 Rs, and platform commission is 30 Rs
//Rider (after ride) --> 132 Rs
//Driver (already had 500 Rs in his wallet) --> (500 + 70) = 570

@Service
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
//    private final PaymentService paymentService;
    private final PaymentRepo paymentRepo;

    public WalletPaymentStrategy(WalletService walletService, PaymentRepo paymentRepo) {
        this.walletService = walletService;
//        this.paymentService = paymentService;
        this.paymentRepo = paymentRepo;
    }

    @Override
    @Transactional
    public void processPayment(Payment payment) {

        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        walletService.deductMoneyFromWallet(rider.getUser(), payment.getAmount(),
                null, payment.getRide(), TransactionMethod.RIDE);

        double driversCut = payment.getAmount() * (1 - PLATFORM_COMMISSION);

        walletService.addMoneyToWallet(driver.getUser(), driversCut,
                null, payment.getRide(), TransactionMethod.RIDE);

//        paymentService.updatePaymentStatus(payment, PaymentStatus.CONFIRMED);

        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepo.save(payment);

    }

}
