package com.meteoauth.MeteoAuth.services;

import com.meteoauth.MeteoAuth.entities.Role;
import com.meteoauth.MeteoAuth.entities.User;
import com.meteoauth.MeteoAuth.oAuth2.GeneralUtils;
import com.meteoauth.MeteoAuth.oAuth2.LocalUser;
import com.meteoauth.MeteoAuth.oAuth2.ResourceNotFoundException;
import com.meteoauth.MeteoAuth.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    new ArrayList<>());
        }

        Collection<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Collection<Role> roles = user.getRoles();
        for (Role role : roles) {
            System.out.println(role.getName());
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
                true, grantedAuthorities);

    }

    public LocalUser loadUserById(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        Set<Role> roles = user.getRoles();
        return new LocalUser(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(roles), user);
    }


}
