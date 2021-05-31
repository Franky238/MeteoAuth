package com.meteoauth.MeteoAuth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private final String SECRET_KEY = "amFub3NlYw==";

    public String extractEmail(String token) {
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

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {// todo roles
        Map<String, Object> claims = new HashMap<>();
    //    for(userDetails.getAuthorities()){
  //         claims.put("authorities",userDetails.getAuthorities());
//            claims.put("READ_PRIVILEGE",true);
       // }
     //   claims.put("role",true);
        return createToken(claims, userDetails.getUsername());
    }

//    public String generateToken(UserDetails userDetails, String stationID) {// todo roles
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("stationID", stationID);
//        return createToken(claims, userDetails.getUsername());
//    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
