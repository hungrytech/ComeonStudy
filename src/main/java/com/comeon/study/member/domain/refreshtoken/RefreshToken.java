package com.comeon.study.member.domain.refreshtoken;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash("refreshToken")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    private String value;

    @TimeToLive
    private  long expiration;

    private RefreshToken(String value, long expiration) {
        this.value = value;
        this.expiration = expiration;
    }

    public static RefreshToken of(String value, long expiration) {
        return new RefreshToken(value, expiration);
    }
}
