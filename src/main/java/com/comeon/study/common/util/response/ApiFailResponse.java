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
public class ApiFailResponse extends ApiResponse {

    private String message;

    private List<String> invalidMessages;

    @Builder
    public ApiFailResponse(String message, List<String> invalidMessages) {
        super(false);
        this.message = message;
        this.invalidMessages = setMessages(invalidMessages);
    }


    private List<String> setMessages(List<String> messages) {
        if (!Objects.isNull(messages) && !messages.isEmpty()) {
            return Collections.unmodifiableList(messages);
        }

        return null;
    }
}