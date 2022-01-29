package com.comeon.study.member.presentation;

import com.comeon.study.common.util.response.ApiResponse;
import com.comeon.study.common.util.response.ApiResponseCreator;
import com.comeon.study.member.application.MemberService;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.MemberLoginRequest;
import com.comeon.study.member.dto.MemberLoginResponse;
import com.comeon.study.member.dto.ReIssuanceTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private static final String SET_COOKIE = "Set-Cookie";

    private static final String REFRESH_TOKEN_COOKIE_NAME = "STUDY_REFRESH";

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<?>> join(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        memberService.join(memberJoinRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseCreator.createSuccessResponse());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> signIn(
            @Valid @RequestBody MemberLoginRequest memberLoginRequest,
            HttpServletResponse response) {

        MemberLoginResponse memberLoginResponse = memberService.signIn(memberLoginRequest);

        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(memberLoginResponse.getRefreshToken());
        response.addHeader(SET_COOKIE, refreshTokenCookie.toString());
        return ResponseEntity.ok()
                .body(ApiResponseCreator.createSuccessResponse(memberLoginResponse.getAccessToken()));
    }

    //TODO: 테스트코드 작성 고민
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> reIssuanceToken(
            @CookieValue(REFRESH_TOKEN_COOKIE_NAME) String refreshToken,
            HttpServletResponse response) {

        ReIssuanceTokenResponse reIssuanceTokenResponse = memberService
                .reIssuanceAccessTokenAndRefreshToken(refreshToken);

        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(reIssuanceTokenResponse.getRefreshToken());
        response.addHeader(SET_COOKIE, refreshTokenCookie.toString());
        return ResponseEntity.ok()
                .body(ApiResponseCreator.createSuccessResponse(reIssuanceTokenResponse.getAccessToken()));
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken) {
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .sameSite("Lax")
                .secure(true)
                .httpOnly(true)
                .path("/")
                .build();
    }
}
