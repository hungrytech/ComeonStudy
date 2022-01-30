package com.comeon.study.auth.accountcontext;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AccountContext extends User {

    private final Long memberId;

    public AccountContext(Long memberId, String password, Collection<? extends GrantedAuthority> authorities) {
        super(String.valueOf(memberId), password, authorities);
        this.memberId = memberId;
    }
}
