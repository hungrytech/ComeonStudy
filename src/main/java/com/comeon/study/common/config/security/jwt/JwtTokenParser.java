package com.comeon.study.common.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenParser extends JwtTokenConfirmManager {

    public JwtTokenParser(@Value("${jwt.secret-key}") String secretKey) {
        super(secretKey);
    }

    public Long getAuthenticatedMemberId(String tokenWithHeader) {
        final Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(removeHeader(tokenWithHeader))
                .getBody();

        return (Long) claims.get("memberId");
    }

}
