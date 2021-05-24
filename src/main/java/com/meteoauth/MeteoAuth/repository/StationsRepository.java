package com.meteoauth.MeteoAuth.repository;

import com.meteoauth.MeteoAuth.entities.Station;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationsRepository extends JpaRepository<Station, Long> {
}
