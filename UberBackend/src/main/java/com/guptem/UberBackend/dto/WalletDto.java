package com.guptem.UberBackend.dto;

//import com.guptem.UberBackend.entities.User;
//import com.guptem.UberBackend.entities.WalletTransaction;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDto {

    private Long id;

    private UserDto user;

    private Double balance;

    private List<WalletTransactionDto> transactions;
}
