package com.michael.Personal.Finance.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private   String  secretKey;
    @Value("${application.security.jwt.expiration}")
    private Long expiration;
    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
       Claims claims = extractAllClaims(token);
       return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
      return Jwts
              .parserBuilder()
              .setSigningKey(getSigningKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
    }



    private Key getSigningKey() {
        byte [] SecreteKeu = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(SecreteKeu);
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username =extractUsername(token);
        return (userDetails.getUsername().equals(username) && !isTokenExpired(token));
    }

    public  boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }


    public Date extractExpiration(String token){
       return  extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ){
        return buildToken(extractClaims, userDetails, expiration);
    }

    private String buildToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails,
            Long expiration) {

        var  authorities = userDetails.
                getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return Jwts.builder()
                .setClaims(extractClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .claim("authorities", authorities)
                .signWith(getSigningKey())
                .compact();

    }
}
