package com.comeon.study.member.presentation;

import com.comeon.study.common.util.response.ApiResponse;
import com.comeon.study.common.util.response.ApiResponseCreator;
import com.comeon.study.member.application.MemberService;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.MemberJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<MemberJoinResponse>> join(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        MemberJoinResponse memberJoinResponse = memberService.join(memberJoinRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseCreator.createSuccessResponse(memberJoinResponse));
    }
}
