package com.comeon.study.member.dto;

import com.comeon.study.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {

    @Email(message = "잘못된 이메일 양식입니다.")
    private String email;

    @NotEmpty(message = "잘못된 닉네임 입니다.")
    private String nickName;

    @NotEmpty(message = "비밀번호는 공백으로 입력할 수 없습니다.")
    private String password;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .nickName(nickName)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
