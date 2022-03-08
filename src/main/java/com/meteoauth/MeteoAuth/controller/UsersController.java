package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.ResouceNotFoundException;
import com.meteoauth.MeteoAuth.assembler.UserAssembler;
import com.meteoauth.MeteoAuth.dto.UserDtoRequest;
import com.meteoauth.MeteoAuth.dto.UserDtoResponse;
import com.meteoauth.MeteoAuth.entities.User;
import com.meteoauth.MeteoAuth.repository.UserRepository;

import com.meteoauth.MeteoAuth.services.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UserRepository userRepository;
    private final UserAssembler userAssembler;
    private final TokenProvider tokenProvider;

    public UsersController(UserRepository userRepository, UserAssembler userAssembler, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDtoResponse> findByEmail(@PathVariable("email") String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResouceNotFoundException("User not found " + email);
        }

        return ResponseEntity.ok().body(userAssembler.getUserDtoResponse(user));
    }

    @GetMapping("")
    public ResponseEntity<List<UserDtoResponse>> getUsers() {
        Iterable<User> userList = userRepository.findAll();
        return ResponseEntity.ok().body(userAssembler.getUserDtoRequestList(userList));
    }

    @PutMapping("{email}")
    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable("email") String email,
                                                      @RequestBody @Valid UserDtoRequest userDtoRequest,
                                                      @RequestHeader(name = "Authorization") String token) {
        String tokenEmail = tokenProvider.extractSubject(token);
        if (tokenEmail.equals(email)) {
            return ResponseEntity.badRequest().build();
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResouceNotFoundException("User not found for this email :: " + email);
        }

        User newUser = userAssembler.getUser(userDtoRequest);
        if (!newUser.getUsername().isEmpty()) {
            user.setUsername(newUser.getUsername());
        }
        if (!newUser.getPassword().isEmpty()) {
            user.setPassword(newUser.getPassword());
        }
        if (!newUser.getEmail().isEmpty()) {
            user.setEmail(newUser.getEmail());
        }
        if (!newUser.getCity().isEmpty()) {
            user.setCity(newUser.getCity());
        }
        userRepository.save(user);
        return ResponseEntity.ok(userAssembler.getUserDtoResponse(user));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteThisUser(@RequestHeader(name = "Authorization") String token) {
        String email = tokenProvider.extractSubject(token);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResouceNotFoundException("User not found for this email :: " + email);
        }
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
