package com.comeon.study.common.util.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private boolean success;

    private T data;

    private String message;

    private List<String> messages;

    @Builder
    public ApiResponse(boolean success, T data, String message, List<String> messages) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.messages = setMessages(messages);
    }

    private List<String> setMessages(List<String> messages) {
        if (!Objects.isNull(messages) && !messages.isEmpty()) {
            return Collections.unmodifiableList(messages);
        }

        return null;
    }
}
