package com.meteoauth.MeteoAuth.services;

import com.meteoauth.MeteoAuth.assembler.UserAssembler;
import com.meteoauth.MeteoAuth.entities.Privilege;
import com.meteoauth.MeteoAuth.entities.Role;
import com.meteoauth.MeteoAuth.entities.User;
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
        }


        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), user.isEnabled() , true, true,
                true, grantedAuthorities); //todo
    }


  //todo new ArrayList<>()Arrays.asList(roleRepository.findByName("ROLE_USER")

//    private Collection<? extends GrantedAuthority> getAuthorities(
//            Collection<Role> roles) {
//        return getGrantedAuthorities(getPrivileges(roles));
//    }
//getAuthorities(user.getRoles())
//    private List<String> getPrivileges(Collection<Role> roles) {
//        List<String> privileges = new ArrayList<>();
//        List<Privilege> collection = new ArrayList<>();
//        for (Role role : roles) {
//
//            collection.addAll(role.getPrivileges());
//        }
//        for (Privilege item : collection) {
//            privileges.add(item.getName());
//        }
//        return privileges;
//    }
//
//    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//  }
}
