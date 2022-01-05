package com.comeon.study.member.application;

import com.comeon.study.member.domain.Member;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.MemberJoinResponse;
import com.comeon.study.member.fixture.MemberFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.comeon.study.member.fixture.MemberFixture.MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRepository = Mockito.mock(MemberRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        memberService = new MemberService(memberRepository, passwordEncoder);
    }

    @Test
    void 회원가입_성공() {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest();

        given(memberRepository.save(any(Member.class))).willReturn(MEMBER);

        // when
        MemberJoinResponse memberJoinResponse = memberService.join(memberJoinRequest);

        // then
        assertAll(
                () -> assertThat(memberJoinResponse.getEmail()).isEqualTo(MEMBER.getEmail()),
                () -> assertThat(memberJoinResponse.getNickName()).isEqualTo(MEMBER.getNickName())
        );
    }

}