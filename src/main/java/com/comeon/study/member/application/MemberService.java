package com.comeon.study.member.application;

import com.comeon.study.auth.jwt.JwtTokenParser;
import com.comeon.study.auth.jwt.JwtTokenProvider;
import com.comeon.study.auth.refreshtoken.RefreshToken;
import com.comeon.study.member.domain.Member;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.auth.refreshtoken.repository.RefreshTokenRepository;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.MemberLoginRequest;
import com.comeon.study.member.dto.MemberLoginResponse;
import com.comeon.study.member.dto.ReIssuanceTokenResponse;
import com.comeon.study.member.exception.ExistingMemberException;
import com.comeon.study.member.exception.NotFoundOrExpiredRefreshTokenException;
import com.comeon.study.member.exception.NotMatchLoginValueException;
import com.comeon.study.member.exception.NotMatchRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(MemberJoinRequest memberJoinRequest) {
        memberRepository.findMemberByEmail(memberJoinRequest.getEmail())
                .ifPresent(member -> {
                    throw new ExistingMemberException();
                });

        memberRepository.save(memberJoinRequest.toMember(passwordEncoder));
    }
}
