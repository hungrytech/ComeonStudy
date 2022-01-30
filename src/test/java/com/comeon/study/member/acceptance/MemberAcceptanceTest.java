package com.comeon.study.member.acceptance;

import com.comeon.study.common.AcceptanceTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원가입_성공() {
        // given
        given(specification)
                .contentType(ContentType.JSON)
                .body(MEMBER_JOIN_REQUEST_JSON)
                .filter(document("user", requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("nickName").description("닉네임"),
                        fieldWithPath("password").description("비밀번호")
                )))
                .filter(getDefaultResponseDocument("user"))

                // when
                .when()
                .post("/api/v1/join")

                // then
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void 회원가입_실패_이미_회원인_경우() {
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

    @Test
    void 회원가입_실패_잘못된_이메일_입력시() {
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
                .body("messages", equalTo(List.of("잘못된 이메일 양식입니다.")));
    }

}
