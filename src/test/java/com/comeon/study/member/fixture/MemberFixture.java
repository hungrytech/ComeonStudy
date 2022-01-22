package com.comeon.study.member.fixture;

import com.comeon.study.member.domain.Member;

public class MemberFixture {

    public static final String MEMBER_JOIN_REQUEST_JSON = "{ \"email\" : \"email@gmail.com\"," +
            " \"nickName\" : \"닉네임\", \"password\" : \"dfeafnpe\" }";

    public static final String INVALID_MEMBER_JOIN_REQUEST_JSON = "{ \"email\" : \"gmail.com\"," +
            " \"nickName\" : \"닉네임\", \"password\" : \"dfeafnpe\" }";

    public static final String LOGIN_REQUEST_JSON = "{ \"email\" : \"test@gmail.com\", \"password\" : \"abcd1234\" }";

    public static final String TEST_EMAIL = "email@gmail.com";
    public static final String TEST_NICKNAME = "닉네임";

    public static final String TEST_MEMBER_LOGIN_EMAIL = "test@gmail.com";
    public static final String TEST_MEMBER_LOGIN_PASSWORD = "abcd1234";
    public static final String TEST_MEMBER_LOGIN_NICKNAME = "테스터1";

    public static final Member MEMBER = new Member("email@gmail.com", "닉네임", "dfadfe2fdfoen");
    public static final Member TEST_LOGIN_MEMBER = new Member(
            "test@gmail.com", "테스터1", "abcd1234");
}
