package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.ResouceNotFoundException;
import com.meteoauth.MeteoAuth.assembler.UserAssembler;
import com.meteoauth.MeteoAuth.dto.UserDtoRequest;
import com.meteoauth.MeteoAuth.dto.UserDtoResponse;
import com.meteoauth.MeteoAuth.entities.User;
import com.meteoauth.MeteoAuth.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/api/users"})
public class UsersController {

    private final UsersRepository usersRepository;
    private final UserAssembler userAssembler;

    @Autowired
    public UsersController(UsersRepository usersRepository, UserAssembler userAssembler) {
        this.usersRepository = usersRepository;
        this.userAssembler = userAssembler;
    }

    @PostMapping("/users") //todo delete /users
    public UserDtoResponse addUser(@RequestBody @Valid UserDtoRequest userDtoRequest) {
        User user = userAssembler.getUser(userDtoRequest);
        user = usersRepository.save(user);
        return userAssembler.getUserDtoResponse(user);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDtoResponse> findById(@PathVariable("id") Long userId) {
        User user = usersRepository.findById(userId).orElseThrow(
                () -> new ResouceNotFoundException("User not found" + userId));
        return ResponseEntity.ok().body(userAssembler.getUserDtoResponse(user));
    }

    @GetMapping({"/users"})
    public  ResponseEntity<List<UserDtoResponse>> getUsers() {
        Iterable<User> userList = usersRepository.findAll();
        return ResponseEntity.ok().body(userAssembler.getUserDtoRequestList(userList));
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable(value = "id") Long userId,
                                                      @RequestBody UserDtoRequest userDtoRequest) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResouceNotFoundException("User not found for this id :: " + userId));
        user.setEmail(userDtoRequest.getEmail());
        usersRepository.save(user);
        return ResponseEntity.ok(userAssembler.getUserDtoResponse(user));
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long userId) {
        User userValues = usersRepository.findById(userId).orElseThrow(
                () -> new ResouceNotFoundException("User not found::: " + userId));
        usersRepository.delete(userValues);
        return ResponseEntity.ok().build();
    }

}
