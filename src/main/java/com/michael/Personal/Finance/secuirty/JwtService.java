package com.michael.Personal.Finance.secuirty;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private   String  secretKey;
    @Value("${application.security.jwt.expiration}")
    private String expiration;
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
}
