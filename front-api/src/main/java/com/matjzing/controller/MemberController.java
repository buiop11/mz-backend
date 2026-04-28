package com.matjzing.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.EmptyResponse;
import com.matjzing.dto.common.ResponseModel;
import com.matjzing.dto.member.*;
import com.matjzing.service.FrontMemberService;
import com.matjzing.util.RestUtil;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = TagsConfig.TAG_LOGIN_01)
@RequestMapping("/api/member")
public class MemberController {

	private final FrontMemberService service;

	@PostMapping
	@Operation(summary ="회원 가입", description =
		  "## Description ##\n"
		+ "회원 가입 API 입니다\n"
		+ "## 에러코드 ##\n"
		+ "코드|설명\n"
		+ "-|-\n"
		+ "ERR_MEMBER_001 | 기존 등록된 회원 존재\n"
		+ "ERR_MEMBER_002 | 이미 사용중인 아이디 입니다.\n"
		+ "ERR_MEMBER_015 | 탈퇴 후 30일동안 가입 불가.\n"
	)
	public ResponseEntity<ResponseModel<FrontMemberInsertResponse>> insert(@Valid @RequestBody FrontMemberInsertRequest req) {
		return RestUtil.ok(service.insert(req));
	}

	@GetMapping("/check/id")
	@Operation(summary ="아이디 중복 체크", description =
		  "## Description ##\n"
		+ "아이디 중복 체크 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<FrontMemberCheckIdResponse>> checkId(@Valid FrontMemberCheckIdRequest req) {
		return RestUtil.ok(service.checkId(req));
	}

	@DeleteMapping()
	@Operation(summary ="회원 탈퇴", description =
		  "## Description ##\n"
		+ "회원 탈퇴 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(@Parameter(hidden = true) CommonMemberSelectRequest req) {
		service.delete(req);
		return RestUtil.ok();
	}

	@Operation(summary ="비밀번호 변경", description =
		  "## Description ##\n"
		+ "비밀번호 변경 API 입니다\n"
		+ "## 에러코드 ##\n"
		+ "코드|설명\n"
		+ "-|-\n"
		+ "ERR_MEMBER_004 | 기존 비밀번호 변경 불가\n"
		+ "ERR_MEMBER_006 | 일치하는 회원 정보가 없는 경우\n"
	)
	@PutMapping("/update-password")
	public ResponseEntity<ResponseModel<EmptyResponse>> updatePassword(@RequestBody FrontMemberPasswordUpdateRequest req) {
		service.updatePassword(req);
		return RestUtil.ok();
	}

}
