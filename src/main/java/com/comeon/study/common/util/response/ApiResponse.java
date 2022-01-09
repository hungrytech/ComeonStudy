package com.comeon.study.common.util.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private boolean success;

    private T data;

    private String message;

    private List<String> messages = new ArrayList<>();

    @Builder
    public ApiResponse(boolean success, T data, String message, List<String> messages) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.messages.addAll(messages);
    }
}
