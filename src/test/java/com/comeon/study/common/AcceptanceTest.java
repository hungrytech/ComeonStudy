package com.comeon.study.common;

import com.comeon.study.common.accetancetool.LoginMemberTool;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.restdocs.restassured3.RestDocumentationFilter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@Import(TestConfig.class)
@ExtendWith({RestDocumentationExtension.class})
@AutoConfigureRestDocs(uriHost = "docs.api.com")
@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    @LocalServerPort
    protected int port;

    @Autowired
    protected LoginMemberTool loginMemberTool;

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
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("api 호출 결과 데이터").optional(),
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
}
