package com.comeon.study.member.dto;

import com.comeon.study.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {

    private String email;

    private String nickName;

    private String password;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .nickName(nickName)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
