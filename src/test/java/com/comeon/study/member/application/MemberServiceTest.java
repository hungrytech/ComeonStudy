package com.comeon.study.member.application;

import com.comeon.study.common.config.security.jwt.JwtTokenParser;
import com.comeon.study.common.config.security.jwt.JwtTokenProvider;
import com.comeon.study.common.config.security.refreshtoken.RefreshToken;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.common.config.security.refreshtoken.repository.RefreshTokenRepository;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.MemberLoginRequest;
import com.comeon.study.member.dto.MemberLoginResponse;
import com.comeon.study.member.dto.ReIssuanceTokenResponse;
import com.comeon.study.member.exception.ExistingMemberException;
import com.comeon.study.member.exception.NotMatchLoginValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static com.comeon.study.member.fixture.TokenFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

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

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(
                memberRepository,
                refreshTokenRepository,
                passwordEncoder,
                jwtTokenProvider,
                jwtTokenParser);
    }

    @Test
    void 회원가입_실패_이미_회원인_경우_예외가_발생한다() {
        // given
        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .email(TEST_MEMBER_LOGIN_EMAIL)
                .nickName(TEST_MEMBER_LOGIN_NICKNAME)
                .password(TEST_MEMBER_LOGIN_PASSWORD)
                .build();

        given(memberRepository.findMemberByEmail(TEST_MEMBER_LOGIN_EMAIL)).willReturn(Optional.of(TEST_LOGIN_MEMBER));

        // when
        assertThatThrownBy(() -> memberService.join(memberJoinRequest)).isInstanceOf(ExistingMemberException.class);

    }

    @Test
    void 로그인_성공() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(TEST_MEMBER_LOGIN_EMAIL, TEST_MEMBER_LOGIN_PASSWORD);
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(TEST_LOGIN_MEMBER));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(refreshTokenRepository.save(any(RefreshToken.class))).willReturn(TEST_REFRESH_TOKEN);

        // when
        MemberLoginResponse memberLoginResponse = memberService.signIn(memberLoginRequest);

        // then
        assertAll(
                () -> assertThat(memberLoginResponse.getNickName()).isEqualTo(TEST_MEMBER_LOGIN_NICKNAME),
                () -> assertThat(memberLoginResponse.getRefreshToken()).isEqualTo(TEST_REFRESH_TOKEN.getValue())
        );
    }

    @Test
    void 로그인_실패_찾는_회원이_없는경우() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(TEST_MEMBER_LOGIN_EMAIL, TEST_MEMBER_LOGIN_PASSWORD);
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> memberService.signIn(memberLoginRequest))
                .isInstanceOf(NotMatchLoginValueException.class);

    }

    @Test
    void 로그인_실패_비밀번호가_다를경우() {
        // given
        MemberLoginRequest memberLoginRequest = new MemberLoginRequest(TEST_MEMBER_LOGIN_EMAIL, TEST_MEMBER_LOGIN_PASSWORD);
        given(memberRepository.findMemberByEmail(anyString())).willReturn(Optional.of(TEST_LOGIN_MEMBER));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when
        assertThatThrownBy(() -> memberService.signIn(memberLoginRequest))
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
        ReIssuanceTokenResponse reissuanceTokenResponse = memberService
                .reIssuanceAccessTokenAndRefreshToken(REQUEST_REFRESH_TOKEN);

        // then
        assertAll(
                () -> assertThat(reissuanceTokenResponse.getAccessToken()).isEqualTo(RE_ISSUANCE_ACCESS_TOKEN),
                () -> assertThat(reissuanceTokenResponse.getAccessToken()).isEqualTo(RE_ISSUANCE_ACCESS_TOKEN)
        );

    }

}