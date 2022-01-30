package com.comeon.study.auth.application;

import com.comeon.study.auth.jwt.JwtTokenParser;
import com.comeon.study.auth.jwt.JwtTokenProvider;
import com.comeon.study.auth.refreshtoken.RefreshToken;
import com.comeon.study.auth.refreshtoken.repository.RefreshTokenRepository;
import com.comeon.study.member.domain.Member;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.member.dto.MemberLoginRequest;
import com.comeon.study.member.dto.MemberLoginResponse;
import com.comeon.study.member.dto.ReIssuanceTokenResponse;
import com.comeon.study.member.exception.NotFoundOrExpiredRefreshTokenException;
import com.comeon.study.member.exception.NotMatchLoginValueException;
import com.comeon.study.member.exception.NotMatchRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtTokenParser jwtTokenParser;

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public MemberLoginResponse signIn(MemberLoginRequest memberLoginRequest) {
        Member member = memberRepository.findMemberByEmail(memberLoginRequest.getEmail())
                .orElseThrow(NotMatchLoginValueException::new);

        if (!passwordEncoder.matches(memberLoginRequest.getPassword(), member.getPassword())) {
            throw new NotMatchLoginValueException();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(member.getId());

        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.of(
                jwtTokenProvider.generateRefreshToken(member.getId()),
                jwtTokenProvider.getRefreshTokenExpirationTime()));

        return new MemberLoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public ReIssuanceTokenResponse reIssuanceAccessTokenAndRefreshToken(String refreshToken) {
        String memberId = jwtTokenParser.getAuthenticatedMemberIdFromRefreshToken(refreshToken);

        RefreshToken existingRefreshToken = refreshTokenRepository.findById(refreshToken)
                .orElseThrow(NotFoundOrExpiredRefreshTokenException::new);

        //같냐?
        if (!existingRefreshToken.isSame(refreshToken)) {
            throw new NotMatchRefreshTokenException();
        }

        refreshTokenRepository.delete(existingRefreshToken);

        RefreshToken reIssuanceRefreshToken = refreshTokenRepository.save(RefreshToken.of(
                jwtTokenProvider.generateRefreshToken(Long.parseLong(memberId)),
                jwtTokenProvider.getRefreshTokenExpirationTime()
        ));

        return new ReIssuanceTokenResponse(
                jwtTokenProvider.generateAccessToken(Long.parseLong(memberId)),
                reIssuanceRefreshToken.getValue());
    }
}
