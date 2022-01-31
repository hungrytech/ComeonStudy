package com.comeon.study.common.accetancetool;

import com.comeon.study.common.util.response.ApiSuccessResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class LoginMemberTool {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String TEST_DOMAIN = "http://localhost:";
    private static final String LOGIN_END_POINT = "/api/v1/login";

    private final HttpHeaders httpHeaders;

    public LoginMemberTool(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public String getAccessToken(int port, String loginMemberJson) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> httpEntity = getHttpEntity(loginMemberJson);

        ResponseEntity<ApiSuccessResponse<String>> exchange = restTemplate.exchange(
                getLoginURL(port),
                HttpMethod.POST,
                httpEntity, new ParameterizedTypeReference<>() {});
        ApiSuccessResponse<String> apiSuccessResponse = exchange.getBody();

        return TOKEN_PREFIX + apiSuccessResponse.getData();
    }

    public String getCookieContainedRefreshToken(int port, String loginMemberJson) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> httpEntity = getHttpEntity(loginMemberJson);

        HttpEntity<String> response = restTemplate.exchange(
                getLoginURL(port),
                HttpMethod.POST,
                httpEntity, String.class);
        HttpHeaders headers = response.getHeaders();

        return headers.getFirst(HttpHeaders.SET_COOKIE);
    }

    private HttpEntity<String> getHttpEntity(String loginMemberJson) {
        return new HttpEntity<>(loginMemberJson, httpHeaders);
    }

    private String getLoginURL(int port) {
        StringBuilder builder = new StringBuilder();
        builder.append(TEST_DOMAIN)
                .append(port)
                .append(LOGIN_END_POINT);

        return builder.toString();
    }
}
