package com.comeon.study.auth.acceptance;

import com.comeon.study.common.AcceptanceTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;


public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("로그인 - 성공")
    @Test
    void login() {
        //given
        given(specification)
                .contentType(ContentType.JSON)
                .body(LOGIN_MEMBER_1_REQUEST_JSON)
                .filter(document("login", requestFields(
                        fieldWithPath("email").description("이메일"),
                        fieldWithPath("password").description("비밀번호")
                )))
                .filter(document("login", responseFields(
                        fieldWithPath("success").description("api 성공여부"),
                        fieldWithPath("data").description("accessToken"),
                        fieldWithPath("message").type(JsonFieldType.NULL).description("에러메세지").optional(),
                        fieldWithPath("messages").type(JsonFieldType.NULL).description("유효성검사 에러 메시지").optional()
                )))

        // when
                .when()
                .post("/api/v1/login")

                // then
                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .cookie("STUDY_REFRESH", notNullValue())
                .body("data", notNullValue());
    }

    @DisplayName("로그인 - 실패 (이메일과 일치하는 회원이 없을 경우)")
    @Test
    void login_fail_not_match_email() {
        // given
        given(specification)
                .contentType(ContentType.JSON)
                .body(LOGIN_ID_FAILED_MEMBER_REQUEST_JSON)

                // when
                .when()
                .post("/api/v1/login")

                // then
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("아이디 혹은 비밀번호가 잘못되었습니다."));
    }

    @DisplayName("로그인 - 실패 (비밀번호가 다를경우)")
    @Test
    void login_fail_not_match_password() {
        // given
        given(specification)
                .contentType(ContentType.JSON)
                .body(LOGIN_PASSWORD_FAILED_MEMBER_REQUEST_JSON)

        // when
                .when()
                .post("/api/v1/login")

        // then
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .assertThat()
                .body("success", equalTo(false))
                .body("message", equalTo("아이디 혹은 비밀번호가 잘못되었습니다."));
    }

}
