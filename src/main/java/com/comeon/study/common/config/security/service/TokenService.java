package com.comeon.study.common.config.security.service;

import com.comeon.study.common.config.security.jwt.JwtTokenParser;
import com.comeon.study.common.config.security.jwt.JwtTokenProvider;
import com.comeon.study.common.config.security.refreshtoken.repository.RefreshTokenRepository;
import com.comeon.study.common.config.security.service.dto.response.RefreshAndAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtTokenParser jwtTokenParser;

    private final RefreshTokenRepository refreshTokenRepository;


    public RefreshAndAccessTokenResponse reIssueToken(String refreshToken) {
        return null;
    }
}
