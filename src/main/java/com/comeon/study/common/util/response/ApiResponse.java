package com.comeon.study.common.util.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ApiResponse {

    private boolean success;

    public ApiResponse(boolean success) {
        this.success = success;
    }
}
