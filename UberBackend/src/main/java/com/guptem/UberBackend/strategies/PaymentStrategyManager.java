package com.guptem.UberBackend.strategies;

import com.guptem.UberBackend.entities.enums.PaymentMethods;
import com.guptem.UberBackend.strategies.impl.CashPaymentStrategy;
import com.guptem.UberBackend.strategies.impl.WalletPaymentStrategy;
import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyManager {

    private final CashPaymentStrategy cashPaymentStrategy;
    private final WalletPaymentStrategy walletPaymentStrategy;

    public PaymentStrategyManager(CashPaymentStrategy cashPaymentStrategy, WalletPaymentStrategy walletPaymentStrategy) {
        this.cashPaymentStrategy = cashPaymentStrategy;
        this.walletPaymentStrategy = walletPaymentStrategy;
    }

    public PaymentStrategy paymentStrategy(PaymentMethods paymentMethods) {

        return switch (paymentMethods) {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };

    }
}
