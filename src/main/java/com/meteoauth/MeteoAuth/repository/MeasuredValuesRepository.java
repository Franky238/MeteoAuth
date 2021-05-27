package com.meteoauth.MeteoAuth.repository;

import com.meteoauth.MeteoAuth.entities.MeasuredValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasuredValuesRepository extends JpaRepository<MeasuredValue, Long> {

}


