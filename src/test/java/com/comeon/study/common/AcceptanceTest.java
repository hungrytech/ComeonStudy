package com.comeon.study.common;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.restdocs.restassured3.RestDocumentationFilter;
import org.springframework.test.context.ActiveProfiles;

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

    protected RestDocumentationFilter getDefaultResponseDocument(String identifier) {
        return document(identifier, responseFields(
                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("api 성공여부"),
                fieldWithPath("data").type(JsonFieldType.NULL).description("api 호출 결과 데이터"),
                fieldWithPath("message").type(JsonFieldType.NULL).description("에러메세지").optional(),
                fieldWithPath("messages").type(JsonFieldType.NULL).description("유효성검사 에러 메시지").optional()
        ));
    }
}
