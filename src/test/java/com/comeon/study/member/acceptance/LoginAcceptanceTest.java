package com.comeon.study.member.acceptance;

import com.comeon.study.common.AcceptanceTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class LoginAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원가입_성공() {
        // given
        given()
                .contentType(ContentType.JSON)
                .body(MEMBER_JOIN_REQUEST_JSON)

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
        given()
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
        given()
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

    @Test
    void 로그인_성공() {
        //given
        given()
                .contentType(ContentType.JSON)
                .body(LOGIN_REQUEST_JSON)
        // when
                .when()
                .post("/api/v1/login")

                // then
                .then()
                .statusCode(HttpStatus.OK.value())
                .assertThat()
                .body("data.nickName", equalTo(TEST_MEMBER_LOGIN_NICKNAME))
                .body("data.accessToken", notNullValue())
                .body("data.refreshToken", notNullValue());
    }

    @Test
    void 로그인_실패_비밀번호가_다를경우() {
        // given
        given()
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

    @Test
    void 로그인_실패_아이디가_다를경우() {
        // given
        given()
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

}
