package com.comeon.study.member.domain.nickname;

import com.comeon.study.member.exception.InvalidNickNameException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NickName {

    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9_-]+$");

    private static final int MAX_LENGTH = 10;

    @Column(name = "nickName")
    private String value;

    private NickName(String value) {
        this.value = value;
    }

    public static NickName of(String value) {
        if (value.isEmpty() || value.length() > MAX_LENGTH) {
            throw new InvalidNickNameException();
        }

        if (!NICKNAME_PATTERN.matcher(value).matches()) {
            throw new InvalidNickNameException();
        }

        return new NickName(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NickName nickName = (NickName) o;
        return Objects.equals(getValue(), nickName.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

