package com.comeon.study.common.config.security.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public abstract class JwtTokenUtil {

    protected String secretKey;

    protected JwtTokenUtil(String secretKey) {
        this.secretKey = Base64.getEncoder()
                .encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
