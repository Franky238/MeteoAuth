package com.meteoauth.MeteoAuth.filtres;

import com.meteoauth.MeteoAuth.entities.Station;
import com.meteoauth.MeteoAuth.repository.StationsRepository;
import com.meteoauth.MeteoAuth.services.JwtUtil;
import com.meteoauth.MeteoAuth.services.MyUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final MyUserDetailsService userDetailsService;
    private final StationsRepository stationsRepository;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(MyUserDetailsService userDetailsService, StationsRepository stationsRepository, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.stationsRepository = stationsRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String subject = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            subject = jwtUtil.extractSubject(jwt);
        }

        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {


            if (jwtUtil.hasRole(jwt, "ROLE_STATION")) {
                Station station = stationsRepository.findById(Long.getLong(subject)).get();

                if (jwtUtil.validateTokenForStation(jwt, station)){
                    SecurityContextHolder.getContext().setAuthentication(new Authentication() {
                        @Override
                        public Collection<? extends GrantedAuthority> getAuthorities() {
                            return null;
                        }

                        @Override
                        public Object getCredentials() {
                            return null;
                        }

                        @Override
                        public Object getDetails() {
                            return null;
                        }

                        @Override
                        public Object getPrincipal() {
                            return null;
                        }

                        @Override
                        public boolean isAuthenticated() {
                            return true;
                        }

                        @Override
                        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

                        }

                        @Override
                        public String getName() {
                            return null;
                        }
                    });
                }
            }



            UserDetails userDetails = this.userDetailsService.loadUserByUsername(subject);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()); //todo
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
