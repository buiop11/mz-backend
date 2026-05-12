package com.matjzing.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.*;
import com.matjzing.dto.login.*;
import com.matjzing.service.FrontLoginService;
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

	private final FrontLoginService service;

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
	public ResponseEntity<ResponseModel<FrontLoginResponse>> login(@Valid @RequestBody FrontLoginRequest req) {
		return RestUtil.ok(service.login(req));
	}

	@PostMapping("/login/google")
	@Operation(summary = "Google 로그인", description =
		  "## Description ##\n"
		+ "Google OIDC `id_token`을 검증한 뒤, 회원이 없으면 MEMBER에 자동 등록하고 access/refresh 토큰을 발급합니다.\n"
		+ "refreshToken은 쿠키로 발급됩니다.\n"
		+ "## 에러코드 ##\n"
		+ "코드|설명\n"
		+ "-|-\n"
		+ "ERR_LOGIN_001 | 회원 조회 실패(등록 직후 오류 등)\n"
		+ "ERR_LOGIN_004 | id_token 검증 실패 또는 google.oauth.client-ids 미설정\n"
	)
	public ResponseEntity<ResponseModel<FrontLoginResponse>> loginWithGoogle(@Valid @RequestBody FrontGoogleLoginRequest req) {
		return RestUtil.ok(service.loginWithGoogle(req));
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
	public ResponseEntity<ResponseModel<FrontLoginResponse>> updateAccessToken(@Parameter(hidden = true) FrontLoginUpdateRefreshTokenRequest req) {
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

//	@GetMapping("/find/id")
//	@Operation(summary ="아이디 찾기", description =
//		  "## Description ##\n"
//		+ "아이디 찾기 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_LOGIN_001 | 일치하는 회원 정보가 없는 경우\n"
//	)
//	public ResponseEntity<ResponseModel<FrontMemberFindIdResponse>> checkId(@Valid FrontMemberFindIdRequest req) {
//		return RestUtil.ok(service.findId(req));
//	}
//
//	@PutMapping("/password-di")
//	@Operation(summary ="DI 비밀번호 변경", description =
//		  "## Description ##\n"
//		+ "DI 비밀번호 변경 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_LOGIN_001 | 일치하는 회원 정보가 없는 경우\n"
//		+ "ERR_LOGIN_003 | 기존 비밀번호화 일치하는 경우\n"
//	)
//	public ResponseEntity<ResponseModel<EmptyResponse>> updatePasswordByDi(@RequestBody FrontMemberPasswordUpdateByDiRequest req) {
//		service.updatePasswordByDi(req);
//		return RestUtil.ok();
//	}
//
//	@PutMapping("/restore-member")
//	@Operation(summary ="휴면 해지", description =
//		  "## Description ##\n"
//		+ "휴면 해지 API 입니다\n"
//		+ "디바이스 정보는 안주셔도 됩니다.\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_LOGIN_001 | 일치하는 회원 정보가 없는 경우\n"
//	)
//	public ResponseEntity<ResponseModel<EmptyResponse>> updateRestoreMember(@Valid @RequestBody FrontRestoreDormancyRequest req) {
//		service.updateRestoreMember(req);
//		return RestUtil.ok();
//	}
//
//	@PutMapping("/swagger/test-dormancy-member")
//	@Operation(summary ="휴면 전환", description =
//		  "## Description ##\n"
//		+ "휴면 전환 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_LOGIN_001 | 일치하는 회원 정보가 없는 경우\n"
//	)
//	public ResponseEntity<ResponseModel<EmptyResponse>> updateDormancyMember(@RequestBody FrontUpdateDormancyMemberRequest req) {
//		service.updateDormancyMember(req);
//		return RestUtil.ok();
//	}
//
//	@PutMapping("/swagger/test-delete-secession-member/{mobilePhoneNo}")
//	@Operation(summary ="탈퇴회원 삭제", description =
//		  "## Description ##\n"
//		+ "탈퇴회원 삭제 API 입니다\n"
//	)
//	public ResponseEntity<ResponseModel<EmptyResponse>> deleteSecessionMember(
//			@ApiParam(value = "전화번호", example = "01012341234", required = true)
//			@PathVariable String mobilePhoneNo) {
//		service.deleteSecessionMember(mobilePhoneNo);
//		return RestUtil.ok();
//	}


}

