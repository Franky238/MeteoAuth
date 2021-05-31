package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.assembler.UserAssembler;
import com.meteoauth.MeteoAuth.dto.AuthenticationRequest;
import com.meteoauth.MeteoAuth.dto.AuthenticationResponse;
import com.meteoauth.MeteoAuth.dto.UserDtoRequest;
import com.meteoauth.MeteoAuth.dto.UserDtoResponse;
import com.meteoauth.MeteoAuth.entities.User;
import com.meteoauth.MeteoAuth.repository.RoleRepository;
import com.meteoauth.MeteoAuth.repository.UserRepository;
import com.meteoauth.MeteoAuth.services.JwtUtil;
import com.meteoauth.MeteoAuth.services.MyUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping({"/api/authentication"})
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final MyUserDetailsService userDetailsService;
    private final UserAssembler userAssembler;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil, MyUserDetailsService userDetailsService, UserAssembler userAssembler, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userAssembler = userAssembler;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public UserDtoResponse addUser(@RequestBody @Valid UserDtoRequest userDtoRequest) {
        User user = userAssembler.getUser(userDtoRequest);

        user.setRoles(Arrays.asList(roleRepository.findByName("USER")));
        user.setEnabled(true);

        user = userRepository.save(user);
        return userAssembler.getUserDtoResponse(user);
    }

}
