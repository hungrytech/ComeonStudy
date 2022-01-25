package com.comeon.study.member.fixture;

import com.comeon.study.common.config.security.refreshtoken.RefreshToken;

public class RefreshTokenFixture {

    public static final RefreshToken TEST_REFRESH_TOKEN = RefreshToken.of("refresh_token", 1800000);
}
