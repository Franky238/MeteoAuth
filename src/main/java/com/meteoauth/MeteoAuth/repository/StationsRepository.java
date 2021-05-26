package com.meteoauth.MeteoAuth.repository;

import com.meteoauth.MeteoAuth.entities.Station;

import com.meteoauth.MeteoAuth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationsRepository extends JpaRepository<Station, Long> {
    Station findByTitle(String title);
    List<Station> findByUser(User user);
}
