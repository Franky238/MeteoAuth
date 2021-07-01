package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.ResouceNotFoundException;
import com.meteoauth.MeteoAuth.assembler.MeasuredValuesAssembler;
import com.meteoauth.MeteoAuth.assembler.StationsAssembler;
import com.meteoauth.MeteoAuth.assembler.UserAssembler;
import com.meteoauth.MeteoAuth.entities.MeasuredValue;
import com.meteoauth.MeteoAuth.entities.Station;
import com.meteoauth.MeteoAuth.entities.User;
import com.meteoauth.MeteoAuth.repository.MeasuredValuesRepository;
import com.meteoauth.MeteoAuth.repository.RoleRepository;
import com.meteoauth.MeteoAuth.repository.StationsRepository;
import com.meteoauth.MeteoAuth.repository.UserRepository;
import com.meteoauth.MeteoAuth.services.JwtUtil;
import com.meteoauth.MeteoAuth.services.MyUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping({"/api/admin"})
public class AdminController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final MyUserDetailsService userDetailsService;
    private final UserAssembler userAssembler;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StationsRepository stationsRepository;
    private final StationsAssembler stationsAssembler;
    private final JwtUtil jwtUtil;
    private final MeasuredValuesRepository measuredValuesRepository;
    private final MeasuredValuesAssembler measuredValuesAssembler;


    public AdminController(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil, MyUserDetailsService userDetailsService, UserAssembler userAssembler,
                           UserRepository userRepository, RoleRepository roleRepository, StationsRepository stationsRepository, StationsAssembler stationsAssembler,
                           JwtUtil jwtUtil, MeasuredValuesRepository measuredValuesRepository, MeasuredValuesAssembler measuredValuesAssembler) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userAssembler = userAssembler;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.stationsRepository = stationsRepository;
        this.stationsAssembler = stationsAssembler;
        this.jwtUtil = jwtUtil;
        this.measuredValuesRepository = measuredValuesRepository;
        this.measuredValuesAssembler = measuredValuesAssembler;
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
