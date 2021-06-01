package com.meteoauth.MeteoAuth.repository;

import com.meteoauth.MeteoAuth.entities.Station;
import com.meteoauth.MeteoAuth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StationsRepository extends JpaRepository<Station, Long> {
    Optional<Station> findById(Long id);
 //   Optional<Station> findByUser(User user);
    Iterable<Station> findByUser(User user);
}
