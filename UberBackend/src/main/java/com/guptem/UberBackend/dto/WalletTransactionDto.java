package com.guptem.UberBackend.dto;

import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.Wallet;
import com.guptem.UberBackend.entities.enums.TransactionMethod;
import com.guptem.UberBackend.entities.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletTransactionDto {

    private Long id;

    private Double amount;

    private TransactionType transactionType;

    private TransactionMethod transactionMethod;

    private RideDto ride;

    private String transactionId;

    private WalletDto wallet;

    private LocalDateTime timeStamp;

}
