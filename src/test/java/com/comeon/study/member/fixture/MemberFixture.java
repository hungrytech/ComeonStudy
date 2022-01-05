package com.comeon.study.member.fixture;

import com.comeon.study.member.domain.Member;

public class MemberFixture {

    public static final String MEMBER_JOIN_REQUEST_JSON = "{ \"email\" : \"email@gmail.com\"," +
            " \"nickName\" : \"닉네임\", \"password\" : \"dfeafnpe\" }";



    public static final Member MEMBER = new Member("email@gmail.com", "닉네임", "dfadfe2fdfoen");
}
