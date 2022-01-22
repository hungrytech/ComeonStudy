package com.comeon.study.member.exception;

import com.comeon.study.common.exception.InvalidValueException;

public class NotMatchLoginValueException extends InvalidValueException {

    private static final String MESSAGE = "아이디 혹은 비밀번호가 잘못되었습니다.";

    public NotMatchLoginValueException() {
        super(MESSAGE);
    }
}
