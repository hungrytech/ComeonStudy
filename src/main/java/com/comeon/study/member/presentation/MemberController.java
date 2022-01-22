package com.comeon.study.member.presentation;

import com.comeon.study.common.config.security.service.accountcontext.AccountContext;
import com.comeon.study.common.util.response.ApiResponse;
import com.comeon.study.common.util.response.ApiResponseCreator;
import com.comeon.study.member.application.MemberService;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.MemberJoinResponse;
import com.comeon.study.member.dto.MemberLoginRequest;
import com.comeon.study.member.dto.MemberLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberLoginResponse>> signIn(
            @Valid @RequestBody MemberLoginRequest memberLoginRequest) {

        MemberLoginResponse memberLoginResponse = memberService.signIn(memberLoginRequest);

        return ResponseEntity.ok()
                .body(ApiResponseCreator.createSuccessResponse(memberLoginResponse));
    }

}
