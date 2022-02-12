package com.comeon.study.member.application;

import com.comeon.study.member.domain.nickname.NickName;
import com.comeon.study.member.domain.repository.MemberRepository;
import com.comeon.study.member.dto.request.MemberJoinRequest;
import com.comeon.study.member.dto.request.NickNameUpdateRequest;
import com.comeon.study.member.exception.AlreadySignedException;
import com.comeon.study.member.exception.InvalidNickNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
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

        given(memberRepository.findMemberByEmail(TEST_MEMBER_LOGIN_EMAIL)).willReturn(Optional.of(TEST_LOGIN_MEMBER_1));

        // when
        assertThatThrownBy(() -> memberService.join(memberJoinRequest)).isInstanceOf(AlreadySignedException.class);

    }

    @DisplayName("회원가입 - 실패 (잘못된 닉네임일 경우)")
    @ParameterizedTest
    @ValueSource(strings = {"", "nickNameLengthOverTen"})
    void join_fail_invalid_nickName(String invalidNickName) {
        // given
        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .email(TEST_MEMBER_LOGIN_EMAIL)
                .nickName(invalidNickName)
                .password(TEST_MEMBER_LOGIN_PASSWORD)
                .build();

        given(memberRepository.findMemberByEmail(TEST_MEMBER_LOGIN_EMAIL)).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> memberService.join(memberJoinRequest)).isInstanceOf(InvalidNickNameException.class);

    }

    @DisplayName("닉네임 변경 - 성공")
    @Test
    void updateNickName() {
        // given
        NickNameUpdateRequest nickNameUpdateRequest = new NickNameUpdateRequest("변경될_닉네임");
        given(memberRepository.findById(TEST_MEMBER_PK_2)).willReturn(Optional.of(TEST_LOGIN_MEMBER_2));

        // when
        memberService.updateNickName(TEST_MEMBER_PK_2, nickNameUpdateRequest);

        // then
        assertThat(TEST_LOGIN_MEMBER_2.getNickName()).isEqualTo(NickName.of(nickNameUpdateRequest.getNickName()));
    }

}