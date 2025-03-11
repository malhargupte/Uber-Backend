package com.guptem.UberBackend.repo;

import com.guptem.UberBackend.entities.Rider;
import com.guptem.UberBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepo extends JpaRepository<Rider, Long> {

    Optional<Rider> findByUser(User user);

}
