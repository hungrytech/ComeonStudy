package com.comeon.study.member.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLoginRequest {

    @Email(message = "잘못된 이메일 양식입니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 공백으로 입력할 수 없습니다.")
    private String password;

    public MemberLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
