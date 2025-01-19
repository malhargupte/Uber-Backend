package com.guptem.UberBackend.repo;

import com.guptem.UberBackend.entities.Payment;
import com.guptem.UberBackend.entities.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
    Optional<Payment> findByRide(Ride ride);
}
