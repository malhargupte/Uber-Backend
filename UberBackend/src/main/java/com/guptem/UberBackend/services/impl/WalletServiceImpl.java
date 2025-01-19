package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.RideDto;
import com.guptem.UberBackend.dto.WalletDto;
import com.guptem.UberBackend.dto.WalletTransactionDto;
import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.User;
import com.guptem.UberBackend.entities.Wallet;
import com.guptem.UberBackend.entities.WalletTransaction;
import com.guptem.UberBackend.entities.enums.TransactionMethod;
import com.guptem.UberBackend.entities.enums.TransactionType;
import com.guptem.UberBackend.exceptions.ResourceNotFoundException;
import com.guptem.UberBackend.repo.WalletRepo;
import com.guptem.UberBackend.services.WalletService;
import com.guptem.UberBackend.services.WalletTransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepo walletRepo;
    private final WalletTransactionService walletTransactionService;
    private final ModelMapper modelMapper;

    public WalletServiceImpl(WalletRepo walletRepo, WalletTransactionService walletTransactionService, ModelMapper modelMapper) {
        this.walletRepo = walletRepo;
        this.walletTransactionService = walletTransactionService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Wallet findbyUser(User user) {

        return walletRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found for user with id: " + user.getId()));

    }

    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double amount, String transactionId,
                                   Ride ride, TransactionMethod transactionMethod) {

        Wallet wallet = findbyUser(user);
        wallet.setBalance(wallet.getBalance() + amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.CREDIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);

        return walletRepo.save(wallet);

    }

    @Override
    @Transactional
    public Wallet deductMoneyFromWallet(User user, Double amount, String transactionId,
                                        Ride ride, TransactionMethod transactionMethod) {

        Wallet wallet = findbyUser(user);
        wallet.setBalance(wallet.getBalance() - amount);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(transactionId)
                .ride(ride)
                .wallet(wallet)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(amount)
                .build();

//        walletTransactionService.createNewWalletTransaction(walletTransaction);
        wallet.getTransactions().add(walletTransaction);

        return walletRepo.save(wallet);

    }

    @Override
    public void withdrawMoneyFromWallet() {

    }

    @Override
    public Wallet findWalletById(Long walletId) {

        return walletRepo.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with id: " + walletId));

    }

    @Override
    public Wallet createNewWallet(User user) {

        Wallet wallet = new Wallet();
        wallet.setUser(user);

        return walletRepo.save(wallet);

    }

}
