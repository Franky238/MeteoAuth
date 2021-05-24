package com.meteoauth.MeteoAuth.services;

import com.meteoauth.MeteoAuth.assembler.UserAssembler;
import com.meteoauth.MeteoAuth.entities.User;
import com.meteoauth.MeteoAuth.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final UserAssembler userAssembler;

    @Autowired
    public MyUserDetailsService(UsersRepository usersRepository, UserAssembler userAssembler) {
        this.usersRepository = usersRepository;
        this.userAssembler = userAssembler;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //todo wrong name of the method
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());  //userAssembler.getUserDtoResponse(user);

    }
}
