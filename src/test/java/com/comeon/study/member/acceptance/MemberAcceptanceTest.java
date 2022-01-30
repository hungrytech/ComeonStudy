package com.comeon.study.member.acceptance;

import com.comeon.study.common.AcceptanceTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class MemberAcceptanceTest extends AcceptanceTest {

    @DisplayName("회원가입 - 성공")
    @Test
    void join() {
        // given
        given(specification)
                .contentType(ContentType.JSON)
                .body(MEMBER_JOIN_REQUEST_JSON)
                .filter(document("user", requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("nickName").description("닉네임"),
                        fieldWithPath("password").description("비밀번호")
                )))
                .filter(getDefaultSuccessResponseDocument("user"))

                // when
                .when()
                .post("/api/v1/join")

                // then
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("회원가입 - 실패 (이미 회원인 경우)")
    @Test
    void join_fail_existing_member() {
        // given
        given(specification)
                .contentType(ContentType.JSON)
                .body(JOINED_TEST_MEMBER_JSON)

                // when
                .when()
                .post("/api/v1/join")

                // then
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("이미 가입된 회원입니다."));
    }

    @DisplayName("회원가입 - 실패 (잘못된 양식의 이메일 입력)")
    @Test
    void join_fail_invalid_email() {
        // given
        given(specification)
                .contentType(ContentType.JSON)
                .body(INVALID_MEMBER_JOIN_REQUEST_JSON)

                // when
                .when()
                .post("/api/v1/join")

                // then
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .assertThat()
                .body("success", equalTo(false))
                .body("invalidMessages", equalTo(List.of("잘못된 이메일 양식입니다.")));
    }

    @DisplayName("닉네임 변경 - 성공")
    @Test
    void updateNickName() {

        // given
        given(specification)
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, getAccessToken())
                .body(MEMBER_UPDATE_NICKNAME_REQUEST_JSON)

                // when
                .when()
                .patch("/api/v1/members/nick-name")

                // then
                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("success", equalTo(true))
                .body("message", equalTo("닉네임이 변경되었습니다."));
    }

}
