package com.guptem.UberBackend.repo;

import com.guptem.UberBackend.entities.User;
import com.guptem.UberBackend.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser(User user);
}
