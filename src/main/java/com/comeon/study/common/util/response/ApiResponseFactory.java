package com.comeon.study.common.util.response;

import com.comeon.study.common.exception.NotInstanceException;

import java.util.List;

public class ApiResponseFactory {

    private ApiResponseFactory() {
        throw new NotInstanceException();
    }

    public static ApiSuccessResponse<?> createSuccessResponseWithEmptyData() {
        return ApiSuccessResponse.builder()
                .data(null)
                .build();
    }

    public static ApiSuccessResponse<?> createSuccessResponseWithEmptyData(String message) {
        return ApiSuccessResponse.builder()
                .data(null)
                .message(message)
                .build();
    }

    public static <T> ApiSuccessResponse<T> createSuccessResponse(T data) {
        return ApiSuccessResponse.<T>builder()
                .data(data)
                .build();
    }

    public static <T> ApiSuccessResponse<T> createSuccessResponse(T data, String message) {
        return ApiSuccessResponse.<T>builder()
                .data(data)
                .message(message)
                .build();
    }

    public static ApiFailResponse createErrorResponse(String message) {
        return ApiFailResponse.builder()
                .message(message)
                .build();
    }

    public static ApiFailResponse createErrorResponse(List<String> messages) {
        return ApiFailResponse.builder()
                .invalidMessages(messages)
                .build();
    }
}
