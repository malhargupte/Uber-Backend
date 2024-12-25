package com.guptem.UberBackend.repo;

import com.guptem.UberBackend.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {

    @Query(
            "SELECT d.*, ST_Distance(d.current_location, :pickUpLocation) AS distance " +
            "FROM drivers AS d " +
            "WHERE available = TRUE AND ST_DWithin(d.current_location, :pickUpLocation, 10000) " +
            "ORDER BY distance " +
            "LIMIT 10"
    )
    List<Driver> findTenNearestDrivers(Point pickUpLocation);
}
