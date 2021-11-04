package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.assembler.UserAssembler;
import com.meteoauth.MeteoAuth.dto.AuthenticationRequest;
import com.meteoauth.MeteoAuth.dto.AuthenticationResponse;
import com.meteoauth.MeteoAuth.dto.UserDtoRequest;
import com.meteoauth.MeteoAuth.dto.UserDtoResponse;
import com.meteoauth.MeteoAuth.entities.Station;
import com.meteoauth.MeteoAuth.entities.User;
import com.meteoauth.MeteoAuth.repository.RoleRepository;
import com.meteoauth.MeteoAuth.repository.StationsRepository;
import com.meteoauth.MeteoAuth.repository.UserRepository;
import com.meteoauth.MeteoAuth.services.MyUserDetailsService;
import com.meteoauth.MeteoAuth.services.TokenProvider;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@PermitAll
@RestController
@RequestMapping({"/api/authentication"})
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final MyUserDetailsService userDetailsService;
    private final UserAssembler userAssembler;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StationsRepository stationsRepository;


    public AuthenticationController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, MyUserDetailsService userDetailsService, UserAssembler userAssembler, UserRepository userRepository, RoleRepository roleRepository, StationsRepository stationsRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
        this.userAssembler = userAssembler;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.stationsRepository = stationsRepository;

    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = tokenProvider.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @RequestMapping(value = "/authenticate-station/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> createAuthenticationTokenForStation(@PathVariable Long id, @RequestParam(required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date expiration) throws Exception {
        Optional<Station> station = stationsRepository.findById(id);

        if (station.isEmpty()) {
            throw new Exception("Station not found");
        }

        final String jwt;
        if (expiration == null) {
            jwt = tokenProvider.generateTokenForStation(station.get());
        } else {
            jwt = tokenProvider.generateTokenForStation(station.get(), expiration);
        }
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public UserDtoResponse addUser(@RequestBody @Valid UserDtoRequest userDtoRequest) {
        User user = userAssembler.getUser(userDtoRequest);

        // user.setRoles(Arrays.asList(roleRepository.findByName("USER")));
        user.setRoles(Set.of(roleRepository.findByName("USER")));
        user.setEnabled(true);

        user = userRepository.save(user);
        return userAssembler.getUserDtoResponse(user);
    }

}
