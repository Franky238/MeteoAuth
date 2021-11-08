package com.meteoauth.MeteoAuth.filtres;

import com.meteoauth.MeteoAuth.entities.Station;
import com.meteoauth.MeteoAuth.repository.StationsRepository;
import com.meteoauth.MeteoAuth.services.MyUserDetailsService;
import com.meteoauth.MeteoAuth.services.StationAuthentication;
import com.meteoauth.MeteoAuth.services.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final MyUserDetailsService userDetailsService;
    private final StationsRepository stationsRepository;
    private final TokenProvider tokenProvider;
    private final StationAuthentication stationAuthentication = new StationAuthentication();

    public JwtRequestFilter(MyUserDetailsService userDetailsService, StationsRepository stationsRepository, TokenProvider tokenProvider) {
        this.userDetailsService = userDetailsService;
        this.stationsRepository = stationsRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (request.getRequestURI().substring(request.getContextPath().length()).equals("/api/authentication/authenticate")){
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");

        String subject = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            subject = tokenProvider.extractSubject(jwt);
        }

        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (tokenProvider.hasRole(jwt, "STATION_ROLE")) {

                Station station = stationsRepository.getOne(Long.parseLong(subject));

                if (tokenProvider.validateTokenForStation(jwt, station)){
                    SecurityContextHolder.getContext().setAuthentication(stationAuthentication);
                    chain.doFilter(request, response);
                    return;
                }
            }

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(subject);

           // if (tokenProvider.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
          //  }
        }
        chain.doFilter(request, response);
    }


}
