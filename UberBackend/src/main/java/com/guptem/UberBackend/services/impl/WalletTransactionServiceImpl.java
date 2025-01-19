package com.guptem.UberBackend.services.impl;

import com.guptem.UberBackend.dto.WalletTransactionDto;
import com.guptem.UberBackend.entities.WalletTransaction;
import com.guptem.UberBackend.repo.WalletTransactionRepo;
import com.guptem.UberBackend.services.WalletTransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

    private final WalletTransactionRepo walletTransactionRepo;
    private final ModelMapper modelMapper;

    public WalletTransactionServiceImpl(WalletTransactionRepo walletTransactionRepo, ModelMapper modelMapper) {
        this.walletTransactionRepo = walletTransactionRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransactionDto) {

        WalletTransaction walletTransaction = modelMapper.map(walletTransactionDto, WalletTransaction.class);
        walletTransactionRepo.save(walletTransaction);

    }
}
