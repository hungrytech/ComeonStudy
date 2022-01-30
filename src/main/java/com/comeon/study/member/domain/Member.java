package com.comeon.study.member.domain;

import com.comeon.study.member.domain.nickname.NickName;
import com.comeon.study.member.domain.role.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Embedded
    private NickName nickName;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, NickName nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
        this.role = Role.ACTIVITY;
    }

    public void updateNickName(NickName nickName) {
        this.nickName = nickName;
    }
}
