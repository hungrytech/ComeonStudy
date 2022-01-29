package com.comeon.study.member.exception;

import com.comeon.study.common.exception.InvalidValueException;

public class NotMatchRefreshTokenException extends InvalidValueException {

    private static final String MESSAGE = "알맞지 않은 토큰입니다. \n 다시 로그인 해주세요.";

    public NotMatchRefreshTokenException() {
        super(MESSAGE);
    }
}
