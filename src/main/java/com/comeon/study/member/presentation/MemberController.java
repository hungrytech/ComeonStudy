package com.comeon.study.member.presentation;

import com.comeon.study.auth.accountcontext.AccountContext;
import com.comeon.study.common.util.response.ApiSuccessResponse;
import com.comeon.study.common.util.response.ApiResponseFactory;
import com.comeon.study.member.application.MemberService;
import com.comeon.study.member.dto.request.MemberJoinRequest;
import com.comeon.study.member.dto.request.NickNameUpdateRequest;
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
    public ResponseEntity<ApiSuccessResponse<?>> join(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        memberService.join(memberJoinRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseFactory.createSuccessResponseWithEmptyData());
    }

    @PatchMapping("/members/nick-name")
    public ResponseEntity<ApiSuccessResponse<?>> updateNickName(
            @AuthenticationPrincipal AccountContext accountContext,
            @RequestBody NickNameUpdateRequest nickNameUpdateRequest) {
        memberService.updateNickName(accountContext.getMemberId(), nickNameUpdateRequest);

        return ResponseEntity.ok()
                .body(ApiResponseFactory.createSuccessResponseWithEmptyData("닉네임이 변경되었습니다."));
    }

}
