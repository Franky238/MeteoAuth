package com.meteoauth.MeteoAuth.services;

import com.meteoauth.MeteoAuth.assembler.UserAssembler;
import com.meteoauth.MeteoAuth.entities.Privilege;
import com.meteoauth.MeteoAuth.entities.Role;
import com.meteoauth.MeteoAuth.entities.User;
import com.meteoauth.MeteoAuth.oAuth2.GeneralUtils;
import com.meteoauth.MeteoAuth.oAuth2.LocalUser;
import com.meteoauth.MeteoAuth.oAuth2.ResourceNotFoundException;
import com.meteoauth.MeteoAuth.oAuth2.UserService;
import com.meteoauth.MeteoAuth.repository.RoleRepository;
import com.meteoauth.MeteoAuth.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final RoleRepository roleRepository;
    private final UserAssembler userAssembler;


    public MyUserDetailsService(UserRepository userRepository, MessageSource messageSource, RoleRepository roleRepository, UserAssembler userAssembler) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.roleRepository = roleRepository;
        this.userAssembler = userAssembler;
    }
    //new code todo customize



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { //todo wrong name of the method
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    new ArrayList<>()); //todo
        }

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Collection<Role> roles = user.getRoles();
        for (Role role : roles) {
            System.out.println(role.getName());
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
           // grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled() , true, true,
                true, grantedAuthorities); //todo

    }

    public LocalUser loadUserById(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<Role> roles = user.getRoles(); // TODO set role
        for (Role role : roles) {
            System.out.println(role.getName());
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            // grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(roles), user);
    }


}
