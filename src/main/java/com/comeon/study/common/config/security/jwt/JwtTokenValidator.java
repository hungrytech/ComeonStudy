package com.comeon.study.common.config.security.jwt;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtTokenValidator extends JwtTokenConfirmManager {

    public JwtTokenValidator(@Value("${jwt.secret-key}") String secretKey) {
        super(secretKey);
    }

    public boolean validateAccessToken(String tokenWithHeader) {
        if (!tokenWithHeader.startsWith(AuthHeader.DECIDED_HEADER_NAME)) {
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

    private Date getCurrentDate() {
        return new Date();
    }
}
