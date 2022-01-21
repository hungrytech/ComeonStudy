package com.comeon.study.common.config.security.entrypoint;

import com.comeon.study.common.util.response.ApiResponseCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationEntrypoint implements AuthenticationEntryPoint {

    private static final String AUTHENTICATION_FAIL_MESSAGE = "유효하지 않은 토큰입니다.";

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntrypoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) {
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, ApiResponseCreator.createErrorResponse(AUTHENTICATION_FAIL_MESSAGE));
        } catch (IOException e) {
            log.error("jwt 비인증 응답처리 실패. 이유 : {}", e.getMessage());
        }
    }
}
