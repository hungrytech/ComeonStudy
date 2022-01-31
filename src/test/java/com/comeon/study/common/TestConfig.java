package com.comeon.study.common;

import com.comeon.study.common.accetancetool.LoginMemberTool;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@TestConfiguration
public class TestConfig {

    @Bean
    public LoginMemberTool loginMemberTool() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new LoginMemberTool(httpHeaders);
    }
}
