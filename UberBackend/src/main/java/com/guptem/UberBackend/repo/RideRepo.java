package com.guptem.UberBackend.repo;

import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepo extends JpaRepository<Ride, Long> {

    Page<Ride> findByDriver(Driver driver, PageRequest pageRequest);

    Page<Ride> findByRider(Rider rider, PageRequest pageRequest);
    
}
