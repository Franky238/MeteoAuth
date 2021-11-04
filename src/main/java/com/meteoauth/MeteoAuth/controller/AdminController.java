package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.ResouceNotFoundException;
import com.meteoauth.MeteoAuth.entities.MeasuredValue;
import com.meteoauth.MeteoAuth.entities.Station;
import com.meteoauth.MeteoAuth.entities.User;
import com.meteoauth.MeteoAuth.repository.MeasuredValuesRepository;
import com.meteoauth.MeteoAuth.repository.StationsRepository;
import com.meteoauth.MeteoAuth.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping({"/api/admin"})
public class AdminController {
    private final UserRepository userRepository;
    private final StationsRepository stationsRepository;
    private final MeasuredValuesRepository measuredValuesRepository;

    public AdminController(UserRepository userRepository, StationsRepository stationsRepository, MeasuredValuesRepository measuredValuesRepository) {
        this.userRepository = userRepository;
        this.stationsRepository = stationsRepository;
        this.measuredValuesRepository = measuredValuesRepository;
    }

    @DeleteMapping("user/{email}")
    public ResponseEntity<Void> deleteEnyUser(@PathVariable("email") String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResouceNotFoundException("User not found for this email :: " + email);
        }
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("station/{id}")
    public ResponseEntity<Void> deleteUserStation(@PathVariable("id") Long id) {
        Optional<Station> stationToDelete = stationsRepository.findById(id);
        stationToDelete.ifPresent(stationsRepository::delete);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("measured-value/{id}")
    public ResponseEntity<Void> deleteMeasuredValues(@PathVariable("id") Long measuredValueID) {
        Optional<MeasuredValue> measuredValue = measuredValuesRepository.findById(measuredValueID);
        measuredValue.ifPresent(measuredValuesRepository::delete);
        return ResponseEntity.ok().build();
    }
}
