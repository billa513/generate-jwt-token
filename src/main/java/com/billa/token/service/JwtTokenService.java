package com.billa.token.service;

import com.billa.token.properties.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenService {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private final TokenProperties tokenProperties;

    public String generateToken(String upn) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("upn", upn);
        claims.put("telPhoneNumber", "12345");
        Map<String, Object> header = new HashMap<>();
        header.put("type", "JWT");
        return doGenerateToken(claims, upn, header);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, Map<String, Object> header) {
        return Jwts.builder().setClaims(claims).setHeader(header).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000)).signWith(SignatureAlgorithm.HS512, tokenProperties.getSecret()).compact();
    }


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(tokenProperties.getSecret()).parseClaimsJws(token).getBody();
    }


    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
