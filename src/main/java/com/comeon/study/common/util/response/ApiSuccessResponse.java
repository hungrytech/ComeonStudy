package com.comeon.study.common.util.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiSuccessResponse<T> {

    private boolean success = true;

    private T data;

    private String message;

    @Builder
    public ApiSuccessResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }
}
