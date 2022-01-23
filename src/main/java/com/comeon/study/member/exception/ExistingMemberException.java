package com.comeon.study.member.exception;

import com.comeon.study.common.exception.InvalidValueException;

public class ExistingMemberException extends InvalidValueException {

    private static final String MESSAGE = "이미 가입된 회원입니다.";

    public ExistingMemberException() {
        super(MESSAGE);
    }
}
