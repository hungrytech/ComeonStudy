package com.comeon.study.member.application;

import com.comeon.study.common.config.security.jwt.JwtTokenProvider;
import com.comeon.study.member.domain.refreshtoken.RefreshToken;
import com.comeon.study.member.domain.Member;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.member.domain.repository.RefreshTokenRepository;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.MemberJoinResponse;
import com.comeon.study.member.dto.MemberLoginRequest;
import com.comeon.study.member.dto.MemberLoginResponse;
import com.comeon.study.member.exception.ExistingMemberException;
import com.comeon.study.member.exception.NotMatchLoginValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;


    public MemberJoinResponse join(MemberJoinRequest memberJoinRequest) {
        memberRepository.findMemberByEmail(memberJoinRequest.getEmail())
                .ifPresent(member -> {
                    throw new ExistingMemberException();
                });

        Member savedMember = memberRepository.save(memberJoinRequest.toMember(passwordEncoder));
        return new MemberJoinResponse(savedMember);
    }

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

        return new MemberLoginResponse(member.getNickName(), accessToken, refreshToken);
    }
}
