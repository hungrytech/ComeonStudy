package com.comeon.study.common;

import com.comeon.study.common.accetancetool.LoginMemberTool;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public LoginMemberTool loginMemberTool() {
        return new LoginMemberTool();
    }
}
