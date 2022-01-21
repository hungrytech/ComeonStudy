package com.comeon.study.common.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider extends JwtTokenUtil {

    private final long expirationTime;

    public JwtTokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.expiration-time}") long expirationTime) {
        super(secretKey);
        this.expirationTime = expirationTime;
    }

    public String generateAccessToken(Long memberId) {
        Claims claims = createClaims(memberId);
        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private Claims createClaims(Long memberId) {
        Claims claims = Jwts.claims();
        claims.put("memberId", memberId);

        return claims;
    }
}
