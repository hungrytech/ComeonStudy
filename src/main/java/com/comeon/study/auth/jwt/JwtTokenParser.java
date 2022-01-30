package com.comeon.study.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenParser extends JwtTokenConfirmManager {

    public JwtTokenParser(@Value("${jwt.secret-key}") String secretKey) {
        super(secretKey);
    }

    public String getAuthenticatedMemberIdFromAccessToken(String tokenWithHeader) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(removeHeader(tokenWithHeader))
                .getBody();

        return String.valueOf(claims.get("memberId"));
    }

    public String getAuthenticatedMemberIdFromRefreshToken(String refreshToken) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(refreshToken)
                .getBody();

        return String.valueOf(claims.get("memberId"));
    }

}
