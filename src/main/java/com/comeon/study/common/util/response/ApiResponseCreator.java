package com.comeon.study.common.util.response;

import com.comeon.study.common.exception.NotInstanceException;

import java.util.List;

public class ApiResponseCreator {

    private ApiResponseCreator() {
        throw new NotInstanceException();
    }

    public static ApiResponse<?> createSuccessResponse() {
        return ApiResponse.builder()
                .success(true)
                .data(null)
                .build();
    }

    public static <T> ApiResponse<T> createSuccessResponse(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> createErrorResponse(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> createErrorResponse(List<String> messages) {
        return ApiResponse.<T>builder()
                .success(false)
                .messages(messages)
                .build();
    }
}
