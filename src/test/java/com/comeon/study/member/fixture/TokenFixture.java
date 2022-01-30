package com.comeon.study.member.fixture;

import com.comeon.study.auth.refreshtoken.RefreshToken;

public class TokenFixture {

    public static final String REQUEST_REFRESH_TOKEN = "refresh_token";
    public static final String REQUEST_EXPIRED_REFRESH_TOKEN = "expired_refresh_token";

    public static final String RE_ISSUANCE_ACCESS_TOKEN = "re_issuance_access_token";

    public static final String TEST_ACCESS_TOKEN = "access_token";
    public static final RefreshToken TEST_REFRESH_TOKEN = RefreshToken.of("refresh_token", 1800000);

    public static final RefreshToken RE_ISSUANCE_REFRESH_TOKEN = RefreshToken.of("re_issuance_refresh_token", 1800000);
}
