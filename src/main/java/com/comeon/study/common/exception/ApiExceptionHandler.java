package com.comeon.study.common.exception;

import com.comeon.study.common.util.response.ApiResponse;
import com.comeon.study.common.util.response.ApiResponseCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<?>> handleApplicationErrorResponse(ApplicationException e) {
        return ResponseEntity.status(e.getHttpStatus())
                        .body(ApiResponseCreator.createErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleBindingException(MethodArgumentNotValidException e) {
        List<String> errorMessages = new ArrayList<>();

        e.getBindingResult()
                .getAllErrors()
                .forEach(error -> errorMessages.add(error.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(ApiResponseCreator.createErrorResponse(errorMessages));
    }
}
