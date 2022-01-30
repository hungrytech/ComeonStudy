package com.comeon.study.common;

import com.comeon.study.common.util.response.ApiSuccessResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.restdocs.restassured3.RestDocumentationFilter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static com.comeon.study.member.fixture.MemberFixture.LOGIN_MEMBER_2_REQUEST_JSON;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriHost = "docs.api.com")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    int port;

    protected RequestSpecification specification;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentationContextProvider) {

        RestAssured.port = port;

        specification = new RequestSpecBuilder().addFilter(
                RestAssuredRestDocumentation.documentationConfiguration(restDocumentationContextProvider))
                .build();

    }

    protected RestDocumentationFilter getDefaultSuccessResponseDocument(String identifier) {
        return document(identifier, responseFields(
                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("api 성공여부"),
                fieldWithPath("data").description("api 호출 결과 데이터").optional(),
                fieldWithPath("message").type(JsonFieldType.NULL).description("성공시 전달 메세지").optional()
        ));
    }

    protected RestDocumentationFilter getDefaultFailResponseDocument(String identifier) {
        return document(identifier, responseFields(
                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("api 성공여부"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("실패시 전달 메세지").optional(),
                fieldWithPath("invalidMessages").type(JsonFieldType.ARRAY).description("값 유효성 에러 메시지").optional()
        ));
    }

    protected String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(LOGIN_MEMBER_2_REQUEST_JSON, httpHeaders);
        ResponseEntity<ApiSuccessResponse<String>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/login",
                HttpMethod.POST,
                httpEntity, new ParameterizedTypeReference<>() {});
        ApiSuccessResponse<String> apiSuccessResponse = exchange.getBody();
        return "Bearer " + apiSuccessResponse.getData();
    }
}
