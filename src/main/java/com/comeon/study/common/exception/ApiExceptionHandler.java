package com.comeon.study.common.exception;

import com.comeon.study.common.util.response.ApiFailResponse;
import com.comeon.study.common.util.response.ApiSuccessResponse;
import com.comeon.study.common.util.response.ApiResponseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiFailResponse> handleApplicationErrorResponse(ApplicationException e) {
        return ResponseEntity.status(e.getHttpStatus())
                        .body(ApiResponseFactory.createErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiFailResponse> handleBindingException(MethodArgumentNotValidException e) {
        List<String> errorMessages = new ArrayList<>();

        e.getBindingResult()
                .getAllErrors()
                .forEach(error -> errorMessages.add(error.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(ApiResponseFactory.createErrorResponse(errorMessages));
    }
}
