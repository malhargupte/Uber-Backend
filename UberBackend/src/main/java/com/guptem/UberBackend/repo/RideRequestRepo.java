package com.guptem.UberBackend.repo;

import com.guptem.UberBackend.entities.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRequestRepo extends JpaRepository<RideRequest, Long> {

}
