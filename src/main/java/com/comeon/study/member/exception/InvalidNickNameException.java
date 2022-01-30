package com.comeon.study.member.exception;

import com.comeon.study.common.exception.InvalidValueException;

public class InvalidNickNameException extends InvalidValueException {

    private static final String MESSAGE = "잘못된 닉네임입니다. 다시 입력해주세요.";

    public InvalidNickNameException() {
        super(MESSAGE);
    }
}
