package com.comeon.study.member.application;

import com.comeon.study.member.domain.Member;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public MemberJoinResponse join(MemberJoinRequest memberJoinRequest) {
        Member savedMember = memberRepository.save(memberJoinRequest.toMember(passwordEncoder));
        return new MemberJoinResponse(savedMember);
    }
}
