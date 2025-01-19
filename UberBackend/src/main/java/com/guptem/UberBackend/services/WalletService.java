package com.guptem.UberBackend.services;

import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.User;
import com.guptem.UberBackend.entities.Wallet;
import com.guptem.UberBackend.entities.enums.TransactionMethod;

public interface WalletService {

    Wallet findbyUser(User user);
    Wallet addMoneyToWallet(User user, Double amount, String transactionId, Ride ride, TransactionMethod transactionMethod);
    Wallet deductMoneyFromWallet(User user, Double amount, String transactionId,
                                 Ride ride, TransactionMethod transactionMethod);
    void withdrawMoneyFromWallet();
    Wallet findWalletById(Long walletId);
    Wallet createNewWallet(User user);

}
