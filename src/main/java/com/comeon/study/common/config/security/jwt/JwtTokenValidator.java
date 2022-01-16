package com.comeon.study.common.config.security.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenValidator {

    private static final String DECIDED_HEADER_NAME = "Bearer ";

    private String secretKey;

    public JwtTokenValidator(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = secretKey;
    }

    @PostConstruct
    private void encodeSecretKey() {
        secretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public boolean validateAccessToken(String tokenWithHeader) {
        if (!tokenWithHeader.startsWith(DECIDED_HEADER_NAME)) {
            return false;
        }

        return validateExpiration(tokenWithHeader);
    }

    private boolean validateExpiration(String tokenWithHeader) {
        try {
            return !Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(removeHeader(tokenWithHeader))
                    .getBody().getExpiration()
                    .before(getCurrentDate());
        } catch (Exception e) {
            log.info("유효한 토큰이 아님: {}", e.getMessage());
            return false;
        }
    }

    private String removeHeader(String tokenWithHeader) {
        return tokenWithHeader.substring(DECIDED_HEADER_NAME.length());
    }

    private Date getCurrentDate() {
        return new Date();
    }
}
