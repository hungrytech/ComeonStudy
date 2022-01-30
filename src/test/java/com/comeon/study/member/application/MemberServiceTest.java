package com.comeon.study.member.application;

import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.exception.ExistingMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @DisplayName("회원가입 - 실패 (이미 회원인 경우)")
    @Test
    void join_fail_existing_member() {
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
}