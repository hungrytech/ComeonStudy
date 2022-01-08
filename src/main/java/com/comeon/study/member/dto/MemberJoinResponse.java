package com.comeon.study.member.dto;

import com.comeon.study.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberJoinResponse {

    private Long id;

    private String email;

    private String nickName;

    public MemberJoinResponse(Member member) {
        id = member.getId();
        email = member.getEmail();
        nickName = member.getNickName();
    }
}
