package com.comeon.study.member.exception;

import com.comeon.study.common.exception.PersistenceException;
import org.springframework.http.HttpStatus;

public class NotFoundMemberException extends PersistenceException {

    private static final String MESSAGE = "회원을 찾을 수 없습니다.";

    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public NotFoundMemberException() {
        super(MESSAGE, HTTP_STATUS);
    }
}
