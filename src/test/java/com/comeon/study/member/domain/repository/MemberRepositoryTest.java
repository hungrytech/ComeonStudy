package com.comeon.study.member.domain.repository;

import com.comeon.study.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.comeon.study.member.fixture.MemberFixture.TEST_MEMBER_LOGIN_EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원가입_성공() {
        // given
        Member member = Member.builder()
                .email("email@gmail.com")
                .nickName("닉네임")
                .password("dnfeo22n2o42pnvd")
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

    @Test
    void 회원이메일을_이용한_회원찾기_성공() {
        // given
        String email = TEST_MEMBER_LOGIN_EMAIL;

        // when
        Optional<Member> memberOptional = memberRepository.findMemberByEmail(email);
        Member member = memberOptional.get();

        // then
        assertThat(member.getEmail()).isEqualTo(email);
    }

}