package com.comeon.study.auth.application;

import com.comeon.study.auth.application.AuthService;
import com.comeon.study.auth.jwt.JwtTokenParser;
import com.comeon.study.auth.jwt.JwtTokenProvider;
import com.comeon.study.auth.refreshtoken.RefreshToken;
import com.comeon.study.auth.refreshtoken.repository.RefreshTokenRepository;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.member.dto.MemberLoginRequest;
import com.comeon.study.member.dto.MemberLoginResponse;
import com.comeon.study.member.dto.ReIssuanceTokenResponse;
import com.comeon.study.member.exception.NotFoundOrExpiredRefreshTokenException;
import com.comeon.study.member.exception.NotMatchLoginValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static com.comeon.study.member.fixture.MemberFixture.TEST_LOGIN_MEMBER;
import static com.comeon.study.member.fixture.TokenFixture.*;
import static com.comeon.study.member.fixture.TokenFixture.RE_ISSUANCE_ACCESS_TOKEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private JwtTokenParser jwtTokenParser;

    @InjectMocks
    private AuthService authService;

    @Test
    void 로그인_성공() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(TEST_MEMBER_LOGIN_EMAIL, TEST_MEMBER_LOGIN_PASSWORD);
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(TEST_LOGIN_MEMBER));
        given(jwtTokenProvider.generateAccessToken(any())).willReturn(TEST_ACCESS_TOKEN);
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(refreshTokenRepository.save(any(RefreshToken.class))).willReturn(TEST_REFRESH_TOKEN);

        // when
        MemberLoginResponse memberLoginResponse = authService.signIn(memberLoginRequest);

        // then
        assertAll(
                () -> assertThat(memberLoginResponse.getAccessToken()).isEqualTo(TEST_ACCESS_TOKEN),
                () -> assertThat(memberLoginResponse.getRefreshToken()).isEqualTo(TEST_REFRESH_TOKEN.getValue())
        );
    }

    @Test
    void 로그인_실패_찾는_회원이_없는경우() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(TEST_MEMBER_LOGIN_EMAIL, TEST_MEMBER_LOGIN_PASSWORD);
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> authService.signIn(memberLoginRequest))
                .isInstanceOf(NotMatchLoginValueException.class);

    }

    @Test
    void 로그인_실패_비밀번호가_다를경우() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(TEST_MEMBER_LOGIN_EMAIL, TEST_MEMBER_LOGIN_PASSWORD);
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(TEST_LOGIN_MEMBER));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when
        assertThatThrownBy(() -> authService.signIn(memberLoginRequest))
                .isInstanceOf(NotMatchLoginValueException.class);
    }

    @Test
    void 토큰_재발급() {
        // given
        given(jwtTokenParser.getAuthenticatedMemberIdFromRefreshToken(anyString())).willReturn("1");
        given(refreshTokenRepository.findById(anyString())).willReturn(Optional.of(TEST_REFRESH_TOKEN));
        given(jwtTokenProvider.generateAccessToken(anyLong())).willReturn(RE_ISSUANCE_ACCESS_TOKEN);
        given(jwtTokenProvider.generateRefreshToken(anyLong())).willReturn(RE_ISSUANCE_ACCESS_TOKEN);
        given(jwtTokenProvider.getRefreshTokenExpirationTime()).willReturn(180000L);
        given(refreshTokenRepository.save(any(RefreshToken.class))).willReturn(RE_ISSUANCE_REFRESH_TOKEN);

        // when
        ReIssuanceTokenResponse reissuanceTokenResponse = authService
                .reIssuanceAccessTokenAndRefreshToken(REQUEST_REFRESH_TOKEN);

        // then
        assertAll(
                () -> assertThat(reissuanceTokenResponse.getAccessToken()).isEqualTo(RE_ISSUANCE_ACCESS_TOKEN),
                () -> assertThat(reissuanceTokenResponse.getAccessToken()).isEqualTo(RE_ISSUANCE_ACCESS_TOKEN)
        );
    }

    @DisplayName("토근 재발급 - 실패 (만료된 리프레시 토큰)")
    @Test
    void reIssuanceToken_fail_expired() {
        // given
        given(jwtTokenParser.getAuthenticatedMemberIdFromRefreshToken(anyString())).willReturn("1");
        given(refreshTokenRepository.findById(anyString())).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> authService.reIssuanceAccessTokenAndRefreshToken(REQUEST_EXPIRED_REFRESH_TOKEN))
                .isInstanceOf(NotFoundOrExpiredRefreshTokenException.class);
    }

}
