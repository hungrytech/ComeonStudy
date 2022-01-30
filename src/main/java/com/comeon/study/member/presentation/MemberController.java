package com.comeon.study.member.presentation;

import com.comeon.study.auth.accountcontext.AccountContext;
import com.comeon.study.common.util.response.ApiResponse;
import com.comeon.study.common.util.response.ApiResponseCreator;
import com.comeon.study.member.application.MemberService;
import com.comeon.study.member.dto.MemberJoinRequest;
import com.comeon.study.member.dto.NickNameUpdateRequest;
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
    public ResponseEntity<ApiResponse<?>> join(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        memberService.join(memberJoinRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseCreator.createSuccessResponse());
    }

    @PatchMapping("/members/nick-name")
    public ResponseEntity<ApiResponse<?>> updateNickName(
            @AuthenticationPrincipal AccountContext accountContext,
            @RequestBody NickNameUpdateRequest nickNameUpdateRequest) {
        memberService.updateNickName(accountContext.getMemberId(), nickNameUpdateRequest);

        return ResponseEntity.ok()
                .body(ApiResponseCreator.createSuccessResponse(null, "닉네임이 변경되었습니다."));
    }

}
