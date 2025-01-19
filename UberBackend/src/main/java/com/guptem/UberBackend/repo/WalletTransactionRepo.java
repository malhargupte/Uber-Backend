package com.guptem.UberBackend.repo;

import com.guptem.UberBackend.entities.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletTransactionRepo extends JpaRepository<WalletTransaction, Long> {
}
