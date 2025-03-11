package com.guptem.UberBackend.repo;

import com.guptem.UberBackend.entities.Driver;
import com.guptem.UberBackend.entities.Rating;
import com.guptem.UberBackend.entities.Ride;
import com.guptem.UberBackend.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepo extends JpaRepository<Rating, Long> {

    List<Rating> findByRider(Rider rider);
    List<Rating> findByDriver(Driver driver);
    Optional<Rating> findByRide(Ride ride);

}
