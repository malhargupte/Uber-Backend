package com.guptem.UberBackend.repo;

import com.guptem.UberBackend.entities.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiderRepo extends JpaRepository<Rider, Long> {

}
