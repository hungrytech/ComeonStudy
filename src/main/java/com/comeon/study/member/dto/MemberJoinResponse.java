package com.comeon.study.member.dto;

import com.comeon.study.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
