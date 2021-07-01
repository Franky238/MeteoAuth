package com.meteoauth.MeteoAuth.repository;

import com.meteoauth.MeteoAuth.entities.MeasuredValue;
import com.meteoauth.MeteoAuth.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeasuredValuesRepository extends JpaRepository<MeasuredValue, Long> {
    Iterable<MeasuredValue> findByStation(Station station);
}


