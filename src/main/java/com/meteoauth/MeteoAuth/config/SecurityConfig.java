package com.meteoauth.MeteoAuth.config;

import com.meteoauth.MeteoAuth.filtres.JwtRequestFilter;
import com.meteoauth.MeteoAuth.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(MyUserDetailsService myUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests().antMatchers("/api/authentication/**").permitAll()
             //  .antMatchers("/api/users/**").hasAnyAuthority("ROLE_USER")
              //  .antMatchers("/api/users").hasAnyAuthority("ROLE_ADMIN")
             // .antMatchers("/api/users","/api/users/**", "/api/stations/**", "/api/measured_values/**").hasRole("USER")


              .antMatchers("/api/users","/api/users/**", "/api/stations/**", "/api/measured_values/**").hasRole("USER")


           //     .antMatchers("/api/admin/**").hasRole("ADMIN")   .hasAnyAuthority("READ_PRIVILEGE")
           //     .antMatchers("/api/users","/api/users/**", "/api/stations/**", "/api/measured_values/**").hasAnyAuthority("WRITE_PRIVILEGE", "READ_PRIVILEGE")

                .anyRequest().authenticated().and().
                exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);


//        httpSecurity.authorizeRequests()
//                .antMatchers("/api/users").hasAnyAuthority("ROLE_USER")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .logout().permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/403");
    }
}
