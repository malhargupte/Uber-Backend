package com.guptem.UberBackend.strategies.impl;

import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Payment;
import com.guptem.UberBackend.entities.Wallet;
import com.guptem.UberBackend.entities.enums.PaymentStatus;
import com.guptem.UberBackend.entities.enums.TransactionMethod;
import com.guptem.UberBackend.repo.PaymentRepo;
import com.guptem.UberBackend.services.PaymentService;
import com.guptem.UberBackend.services.WalletService;
import com.guptem.UberBackend.strategies.PaymentStrategy;
import org.springframework.stereotype.Service;

//Rider : 100 Rs
//Driver : 70 Rs (commission); deduct 30 Rs from driver's wallet

@Service
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletService walletService;
//    private final PaymentService paymentService;
    private final PaymentRepo paymentRepo;

    public CashPaymentStrategy(WalletService walletService, PaymentRepo paymentRepo) {
        this.walletService = walletService;
//        this.paymentService = paymentService;
        this.paymentRepo = paymentRepo;
    }

    @Override
    public void processPayment(Payment payment) {

        Driver driver = payment.getRide().getDriver();

//        Wallet driverWallet = walletService.findbyUser(driver.getUser());
        double platformCommission = payment.getAmount() * PLATFORM_COMMISSION;

        walletService.deductMoneyFromWallet(driver.getUser(), platformCommission,
                null, payment.getRide(), TransactionMethod.RIDE);

//        paymentService.updatePaymentStatus(payment, PaymentStatus.CONFIRMED);
        payment.setPaymentStatus(PaymentStatus.CONFIRMED);
        paymentRepo.save(payment);

    }
}
