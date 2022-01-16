package com.comeon.study.common.exception;

import org.springframework.http.HttpStatus;

public class PersistenceException extends ApplicationException{

    public PersistenceException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
