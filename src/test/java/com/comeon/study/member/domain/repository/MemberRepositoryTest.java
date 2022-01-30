package com.comeon.study.member.domain.repository;

import com.comeon.study.member.domain.Member;
import com.comeon.study.member.domain.nickname.NickName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원가입 - 성공")
    @Test
    void join() {
        // given
        Member member = Member.builder()
                .email(TEST_EMAIL)
                .nickName(NickName.of(TEST_NICKNAME))
                .password(TEST_PASSWORD)
                .build();

        // when
        Member resultMember = memberRepository.save(member);

        // then
        assertAll(
                () -> assertThat(resultMember.getEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(resultMember.getNickName()).isEqualTo(member.getNickName()),
                () -> assertThat(resultMember.getPassword()).isEqualTo(member.getPassword())
        );
    }

    @DisplayName("회원가입 - 실패 (이미 회원인 경우)")
    @Test
    void join_fail_existing_member() {
        // given
        String email = TEST_MEMBER_LOGIN_EMAIL;

        // when
        Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);
        Member member = memberOptional.get();

        // then
        assertThat(member.getEmail()).isEqualTo(email);
    }
}