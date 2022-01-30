package com.comeon.study.auth.service;

import com.comeon.study.auth.jwt.JwtTokenParser;
import com.comeon.study.auth.jwt.JwtTokenProvider;
import com.comeon.study.auth.refreshtoken.repository.RefreshTokenRepository;
import com.comeon.study.auth.service.dto.response.RefreshAndAccessTokenResponse;
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
