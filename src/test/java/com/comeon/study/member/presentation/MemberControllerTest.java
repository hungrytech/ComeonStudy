package com.comeon.study.member.presentation;

import com.comeon.study.common.ControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest extends ControllerTest {

    @Test
    void 회원가입_성공() throws Exception {
        // given
        ResultActions perform = mockMvc.perform(post("/api/v1/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MEMBER_JOIN_REQUEST_JSON));

        perform.andExpect(status().isCreated());
    }

    @Test
    void 회원가입_실패_이미_회원인_경우() throws Exception {
        ResultActions perform = mockMvc.perform(post("/api/v1/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JOINED_TEST_MEMBER_JSON));

        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value("이미 가입된 회원입니다."));

    }

    @Test
    void 회원가입_실패_잘못된_이메일_입력시() throws Exception {
        ResultActions perform = mockMvc.perform(post("/api/v1/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_MEMBER_JOIN_REQUEST_JSON));

        perform.andExpect(status().isBadRequest())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("messages").value("잘못된 이메일 양식입니다."));
    }

    @Test
    void 로그인_성공() throws Exception {
        ResultActions perform = mockMvc.perform(post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(LOGIN_REQUEST_JSON));

        perform.andExpect(status().isOk())
                .andExpect(jsonPath("data.nickName").value(TEST_MEMBER_LOGIN_NICKNAME));
    }
}
