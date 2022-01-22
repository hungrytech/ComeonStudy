package com.comeon.study.member.presentation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    void 회원가입_성공() throws Exception {
        ResultActions perform = mockMvc.perform(post("/api/v1/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MEMBER_JOIN_REQUEST_JSON));

        perform.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("data.email").value(TEST_EMAIL))
                .andExpect(jsonPath("data.nickName").value(TEST_NICKNAME));
    }

    @Test
    void 잘못된_이메일_입력시_회원가입_실패() throws Exception {
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
