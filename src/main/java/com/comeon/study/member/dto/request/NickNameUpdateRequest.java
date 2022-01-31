package com.comeon.study.member.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NickNameUpdateRequest {

    private String nickName;

    public NickNameUpdateRequest(String nickName) {
        this.nickName = nickName;
    }
}
