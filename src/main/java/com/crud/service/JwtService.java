package com.crud.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtService {
    private static final String DEFAULT_SECRET = "c2VjdXJlLXNhbXBsZS1qd3Qtc2VjcmV0LWtleS0xMjM0NTY3ODk=";

    private final String secret;

    public JwtService(@Value("${jwt.secret:}") String secret) {
        this.secret = secret;
    }

    public String extractUsername(String token) {
        log.info("START::extractUsername()");
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        log.info("START::Checking extractExpiration");
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        log.info("START::extractClaim()");
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        log.info("START::Checking isTokenExpired");
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        log.info("START :: validateToken::");
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String generateToken(String userName) {
        log.info("START:: JwtService --> generateToken");
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        String configuredSecret = (secret == null || secret.isBlank()) ? DEFAULT_SECRET : secret;
        byte[] keyBytes = Decoders.BASE64.decode(configuredSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
