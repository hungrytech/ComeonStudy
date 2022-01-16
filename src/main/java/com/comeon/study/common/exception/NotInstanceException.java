package com.comeon.study.common.exception;

public class NotInstanceException extends RuntimeException{

    private static final String MESSAGE = "인스턴스화 할 수 없습니다.";

    public NotInstanceException() {
        super(MESSAGE);
    }
}
