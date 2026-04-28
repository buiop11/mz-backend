package com.matjzing.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.*;
import com.matjzing.dto.login.*;
import com.matjzing.service.AdminLoginService;
import com.matjzing.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = TagsConfig.TAG_LOGIN_01)
@RequestMapping("/api")
public class LoginController {

	private final AdminLoginService service;

	@PostMapping("/login")
	@Operation(summary ="로그인", description =
		  "## Description ##\n"
		+ "로그인 API 입니다\n"
		+ "refreshToken은 쿠키로 발급됩니다.\n"
		+ "## 에러코드 ##\n"
		+ "코드|설명\n"
		+ "-|-\n"
		+ "ERR_LOGIN_001 | 일치하는 회원 정보가 없는 경우\n"
		+ "ERR_LOGIN_002 | 휴면 회원 전환 실패\n"
	)
	public ResponseEntity<ResponseModel<AdminLoginResponse>> login(@Valid @RequestBody AdminLoginRequest req) {
		return RestUtil.ok(service.login(req));
	}

	@PutMapping("/access-token")
	@Operation(summary ="access 토큰 갱신", description =
		  "## Description ##\n"
		+ "access 토큰 갱신 API 입니다\n"
		+ "refresh 토큰 만료 15일 전이면 refresh token 새로 발급\n"
		+ "## 에러코드 ##\n"
		+ "코드|설명\n"
		+ "-|-\n"
		+ "ERR_LOGIN_002 | 리프레시 토큰이 없는 경우\n"
	)
	public ResponseEntity<ResponseModel<AdminLoginResponse>> updateAccessToken(@Parameter(hidden = true) AdminLoginUpdateRefreshTokenRequest req) {
		return RestUtil.ok(service.updateAccessToken(req));
	}

	@GetMapping("/logout")
	@Operation(summary ="로그아웃", description =
		  "## Description ##\n"
		+ "로그아웃 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> logout() {
		service.logout();
		return RestUtil.ok();
	}

//	@GetMapping("/find-password")
//	@Operation(summary ="비밀번호 찾기", description =
//		  "## Description ##\n"
//		+ "비밀번호 찾기 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_LOGIN_001 | 일치하는 회원 정보가 없는 경우\n"
//	)
//	public ResponseEntity<ResponseModel<AdminFindPasswordResponse>> findPassword(AdminFindPasswordRequest req) {
//		return RestUtil.ok(service.findPassword(req));
//	}
//
//	@PutMapping("/password")
//	@Operation(summary ="비밀번호 변경", description =
//		  "## Description ##\n"
//		+ "비밀번호 변경 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_LOGIN_001 | 일치하는 회원 정보가 없는 경우\n"
//	)
//	public ResponseEntity<ResponseModel<EmptyResponse>> updatePassword(@Valid @RequestBody AdminUpdatePasswordRequest req) {
//		service.updatePassword(req);
//		return RestUtil.ok();
//	}

}

