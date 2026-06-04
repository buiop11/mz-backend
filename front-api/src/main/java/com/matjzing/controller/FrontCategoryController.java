package com.matjzing.controller;

import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.*;
import com.matjzing.dto.category.*;
import com.matjzing.service.FrontCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.matjzing.util.RestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 컨트롤러 작성
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = TagsConfig.TAG_CATEGORY_01)
@RequestMapping("/api/category")
public class FrontCategoryController {
	
	private final FrontCategoryService service;
	
	@GetMapping("/list")
	@Operation(summary ="카테고리 List 조회", description =
		  "## Description ##\n"
		+ "카테고리 List 조회 API 입니다\n"
		+ "공통 기본(MEMBER_SEQ=0) + 로그인 회원 개인 카테고리를 함께 조회합니다.\n"
	)
	public ResponseEntity<ResponseModel<List<FrontCategorySelectListResponse>>> list(FrontCategorySelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary ="카테고리 Page 조회", description =
		  "## Description ##\n"
		+ "카테고리 Page 조회 API 입니다\n"
		+ "공통 기본(MEMBER_SEQ=0) + 로그인 회원 개인 카테고리를 함께 조회합니다.\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<FrontCategorySelectPageResponse>>> page(FrontCategorySelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@GetMapping("/{categorySeq}")
	@Operation(summary ="카테고리 상세 조회", description =
		  "## Description ##\n"
		+ "카테고리 상세 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<FrontCategorySelectResponse>> detail(@PathVariable("categorySeq") Long categorySeq, @Parameter(hidden = true) FrontCategorySelectRequest req) {
		req.setCategorySeq(categorySeq);
		return RestUtil.ok(service.detail(req));
	}

	@PostMapping
	@Operation(summary ="카테고리 등록", description =
		  "## Description ##\n"
		+ "카테고리 등록 API 입니다 (개인 카테고리, MEMBER_SEQ=로그인 회원)\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody FrontCategoryInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary ="카테고리 수정", description =
		  "## Description ##\n"
		+ "카테고리 수정 API 입니다 (본인 개인 카테고리만, 기본 MEMBER_SEQ=0 불가)\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody FrontCategoryUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{categorySeq}")
	@Operation(summary ="카테고리 삭제", description =
		  "## Description ##\n"
		+ "카테고리 삭제 API 입니다 (본인 개인 카테고리만, MEMBER_SEQ=0 기본 카테고리 불가)\n"
		+ "로그인 JWT가 있으면 memberSeq 생략 가능합니다.\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(
			@PathVariable("categorySeq") Long categorySeq,
			@Parameter(description = "로그인 회원 MEMBER_SEQ (access JWT에 회원 정보가 있으면 생략 가능)")
			@RequestParam(required = false) Long memberSeq,
			@Parameter(hidden = true) @ModelAttribute FrontCategoryDeleteRequest req) {
		req.setCategorySeq(categorySeq);
		if (memberSeq != null) {
			req.setMemberSeq(memberSeq);
		}
		service.delete(req);
		return RestUtil.ok();
	}

}
