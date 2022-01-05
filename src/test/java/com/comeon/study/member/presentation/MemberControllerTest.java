package com.comeon.study.member.presentation;

import com.comeon.study.member.fixture.MemberFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.comeon.study.member.fixture.MemberFixture.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 회원가입_성공() throws Exception {
        ResultActions perform = mockMvc.perform(post("/api/v1/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(MEMBER_JOIN_REQUEST_JSON));

        perform.andExpect(status().isCreated())
                .andExpect(jsonPath("email").value("email@gmail.com"))
                .andExpect(jsonPath("nickName").value("닉네임"));
    }

}
