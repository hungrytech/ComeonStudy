package com.comeon.study.member.application;

import com.comeon.study.member.domain.Member;
import com.comeon.study.member.domain.nickname.NickName;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.NickNameUpdateRequest;
import com.comeon.study.member.exception.ExistingMemberException;
import com.comeon.study.member.exception.NotFoundMemberException;
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

    public void updateNickName(Long memberId, NickNameUpdateRequest nickNameUpdateRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);

        member.updateNickName(NickName.of(nickNameUpdateRequest.getNickName()));
    }
}
