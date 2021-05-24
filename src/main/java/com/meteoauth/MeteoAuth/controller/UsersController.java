package com.meteoauth.MeteoAuth.controller;

import com.meteoauth.MeteoAuth.ResouceNotFoundException;
import com.meteoauth.MeteoAuth.assembler.UserAssembler;
import com.meteoauth.MeteoAuth.dto.UserDtoRequest;
import com.meteoauth.MeteoAuth.dto.UserDtoResponse;
import com.meteoauth.MeteoAuth.entities.User;
import com.meteoauth.MeteoAuth.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersRepository usersRepository;
    private final UserAssembler userAssembler;

    @Autowired
    public UsersController(UsersRepository usersRepository, UserAssembler userAssembler) {
        this.usersRepository = usersRepository;
        this.userAssembler = userAssembler;
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDtoResponse> findByEmail(@PathVariable("email") String email) {
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new ResouceNotFoundException("User not found " + email);
        }

        return ResponseEntity.ok().body(userAssembler.getUserDtoResponse(user));
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDtoResponse> findById(@PathVariable("id") Long userId) {
//        User user = usersRepository.findById(userId).orElseThrow(
//                () -> new ResouceNotFoundException("User not found" + userId));
//        return ResponseEntity.ok().body(userAssembler.getUserDtoResponse(user));
//    }

    @GetMapping("")
    public ResponseEntity<List<UserDtoResponse>> getUsers() {
        Iterable<User> userList = usersRepository.findAll();
        return ResponseEntity.ok().body(userAssembler.getUserDtoRequestList(userList));
    }

    @PutMapping("{email}")
    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable("email") String email,
                                                      @RequestBody @Valid UserDtoRequest userDtoRequest) {
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new ResouceNotFoundException("User not found for this email :: " + email);
        }
        User newUser =  userAssembler.getUser(userDtoRequest);
        if(newUser.getUsername() != null ||!newUser.getUsername().isEmpty() ){
            user.setUsername(newUser.getUsername());
        }
        if(!newUser.getPassword().isEmpty()){ //todo not working
            user.setPassword(newUser.getPassword());
        }
        if (!newUser.getEmail().isEmpty()) {
            user.setEmail(newUser.getEmail());
        }
        if(!newUser.getCity().isEmpty()){
            user.setCity(newUser.getCity());
        }

        usersRepository.save(user);
        return ResponseEntity.ok(userAssembler.getUserDtoResponse(user));
    }
//    @PutMapping("{id}")
//    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable(value = "id") Long userId,
//                                                      @RequestBody UserDtoRequest userDtoRequest) {
//        User user = usersRepository.findById(userId)
//                .orElseThrow(() -> new ResouceNotFoundException("User not found for this id :: " + userId));
//        user.setEmail(userDtoRequest.getEmail());
//        usersRepository.save(user);
//        return ResponseEntity.ok(userAssembler.getUserDtoResponse(user));
//    }

    @DeleteMapping("{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable("email") String email) {
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new ResouceNotFoundException("User not found for this email :: " + email);
        }
        usersRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
