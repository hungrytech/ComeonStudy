package com.comeon.study.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidValueException extends ApplicationException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public InvalidValueException(String message) {
        super(message, HTTP_STATUS);
    }
}
