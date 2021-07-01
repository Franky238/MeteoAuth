package com.meteoauth.MeteoAuth.services;

import com.meteoauth.MeteoAuth.entities.Station;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Transactional //todo
@Service
public class JwtUtil {
    private final String SECRET_KEY = "amFub3NlYw==";

    public String extractSubject(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return extractClaim(token, Claims::getSubject);
    }

//    public String extractStationID(String token) {
//        if (token.startsWith("Bearer ")) {
//            token = token.substring(7);
//        }
//        return extractClaim(token, Claims::getId);
//    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public boolean hasRole(String token, String role) {
        final Claims claims = extractAllClaims(token);
        Object authorities = claims.get("authorities");
        return authorities.toString().contains(role);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", "ROLE_USER");
        return createToken(claims, userDetails.getUsername());
    }

    public String generateTokenForStation(Station station) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", "ROLE_STATION");
        return createStationToken(claims, station.getId().toString());
    }

    public String generateTokenForStation(Station station, Date expiration) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", "ROLE_STATION");
        return createStationToken(claims, station.getId().toString(), expiration);
    }


    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    private String createStationToken(Map<String, Object> claims, String subject, Date expiration) throws Exception {
        if (expiration.before(new Date())) {
            throw new Exception("Expiration cannot be before now");
        }
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    private String createStationToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractSubject(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateTokenForStation(String token, Station station) {
        final Long id = Long.parseLong(extractSubject(token));
        return (id.equals(station.getId())); //todo && !isTokenExpired(token)
    }
}
