package com.example.springbootclient.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    @Value("${jwt.token.access-expired-time}")
    private long ACCESS_EXPIRED_TIME;

    @Value("${jwt.token.refresh-expired-time}")
    private long REFRESH_EXPIRED_TIME;

    @Value("${jwt.token.secret}")
    private String SECRET;

    public String createJwtAccessToken(String userName) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);

        return Jwts.builder()
            .addClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRED_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }

    public String createJwtRefreshToken() {
        Claims claims = Jwts.claims();
        claims.put("value", UUID.randomUUID());

        return Jwts.builder()
            .addClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(
                new Date(System.currentTimeMillis() + REFRESH_EXPIRED_TIME)
            )
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();
    }

    public String getUserName(String token) {
        return getClaimsFromJwtToken(token).get("userName", String.class);
    }

    public boolean isExpired(String token) {
        return getClaimsFromJwtToken(token).getExpiration().before(new Date());
    }

    public Date getExpiredTime(String token) {
        return getClaimsFromJwtToken(token).getExpiration();
    }

    private Claims getClaimsFromJwtToken(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
