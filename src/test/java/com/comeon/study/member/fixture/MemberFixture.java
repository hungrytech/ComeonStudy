package com.comeon.study.member.fixture;

import com.comeon.study.member.domain.Member;
import com.comeon.study.member.domain.nickname.NickName;

public class MemberFixture {

    public static final String MEMBER_JOIN_REQUEST_JSON = "{ \"email\" : \"email@gmail.com\"," +
            " \"nickName\" : \"닉네임\", \"password\" : \"dfeafnpe\" }";

    public static final String JOINED_TEST_MEMBER_JSON = "{ \"email\" : \"test1@gmail.com\"," +
            " \"nickName\" : \"닉네임\", \"password\" : \"abcd1234\" }";

    public static final String INVALID_MEMBER_JOIN_REQUEST_JSON = "{ \"email\" : \"gmail.com\"," +
            " \"nickName\" : \"닉네임\", \"password\" : \"dfeafnpe\" }";

    public static final String MEMBER_UPDATE_NICKNAME_REQUEST_JSON = "{\"nickName\": \"바뀐닉네임\"}";

    public static final String LOGIN_MEMBER_1_REQUEST_JSON = "{ \"email\" : \"test1@gmail.com\", \"password\" : \"abcd1234\" }";
    public static final String LOGIN_MEMBER_2_REQUEST_JSON = "{ \"email\" : \"test2@gmail.com\", \"password\" : \"abcd1234\" }";
    public static final String LOGIN_PASSWORD_FAILED_MEMBER_REQUEST_JSON = "{ \"email\" : \"test@gmail.com\", \"password\" : \"failedPassword\" }";
    public static final String LOGIN_ID_FAILED_MEMBER_REQUEST_JSON = "{ \"email\" : \"faild@gmail.com\", \"password\" : \"abcd1234\" }";

    public static final Long TEST_MEMBER_PK_1 = 1L;
    public static final Long TEST_MEMBER_PK_2 = 2L;

    public static final String TEST_EMAIL = "email@gmail.com";
    public static final String TEST_NICKNAME = "닉네임";
    public static final String TEST_PASSWORD = "dfadfe2fdfoen";

    public static final String TEST_MEMBER_LOGIN_EMAIL = "test@gmail.com";
    public static final String TEST_MEMBER_LOGIN_PASSWORD = "abcd1234";
    public static final String TEST_MEMBER_LOGIN_NICKNAME = "테스터1";

    public static final Member MEMBER = new Member("email@gmail.com", NickName.of("닉네임"), "dfadfe2fdfoen");
    public static final Member TEST_LOGIN_MEMBER_1 = new Member(
            "test@gmail.com", NickName.of("테스터1"), "abcd1234");

    public static final Member TEST_LOGIN_MEMBER_2 = new Member(
            "test2@gmail.com", NickName.of("테스터2"), "abcd1234");
}
