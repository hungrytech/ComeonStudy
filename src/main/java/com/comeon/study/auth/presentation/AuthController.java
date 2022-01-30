package com.comeon.study.auth.presentation;

import com.comeon.study.auth.application.AuthService;
import com.comeon.study.auth.jwt.JwtTokenProvider;
import com.comeon.study.common.util.response.ApiSuccessResponse;
import com.comeon.study.common.util.response.ApiResponseFactory;
import com.comeon.study.member.dto.MemberLoginRequest;
import com.comeon.study.member.dto.MemberLoginResponse;
import com.comeon.study.member.dto.ReIssuanceTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private static final String SET_COOKIE = "Set-Cookie";
    private static final String REFRESH_TOKEN_COOKIE_NAME = "STUDY_REFRESH";

    @Value("${cookie.properties.domain}")
    private String cookieDomainValue;

    private final AuthService authService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<String>> signIn(
            @Valid @RequestBody MemberLoginRequest memberLoginRequest,
            HttpServletResponse response) {

        MemberLoginResponse memberLoginResponse = authService.signIn(memberLoginRequest);

        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(
                memberLoginResponse.getRefreshToken(),
                jwtTokenProvider.getRefreshTokenExpirationTime());
        response.addHeader(SET_COOKIE, refreshTokenCookie.toString());
        return ResponseEntity.ok()
                .body(ApiResponseFactory.createSuccessResponse(memberLoginResponse.getAccessToken()));
    }

    //TODO: 테스트코드 작성 고민
    @PostMapping("/refresh")
    public ResponseEntity<ApiSuccessResponse<String>> reIssuanceToken(
            @CookieValue(value = REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshToken,
            HttpServletResponse response) {

        ReIssuanceTokenResponse reIssuanceTokenResponse = authService
                .reIssuanceAccessTokenAndRefreshToken(refreshToken);

        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(
                reIssuanceTokenResponse.getRefreshToken(),
                jwtTokenProvider.getRefreshTokenExpirationTime());
        response.addHeader(SET_COOKIE, refreshTokenCookie.toString());
        return ResponseEntity.ok()
                .body(ApiResponseFactory.createSuccessResponse(reIssuanceTokenResponse.getAccessToken()));
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken, Long expiredRefreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .sameSite("Lax")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .maxAge(expiredRefreshToken)
                .domain(cookieDomainValue)
                .build();
    }
}
