package com.comeon.study.member.exception;

import com.comeon.study.common.exception.InvalidValueException;

public class NotFoundOrExpiredRefreshTokenException extends InvalidValueException {

    private static final String MESSAGE = "잘못된 토큰이거나, 만료된 토큰입니다. \n 다시 로그인 해주세요.";

    public NotFoundOrExpiredRefreshTokenException() {
        super(MESSAGE);
    }
}
