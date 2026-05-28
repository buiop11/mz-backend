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
 * public static final String TAG_CATEGORY_01 = "02.카테고리"; // 번호는 채번 룰에따라
 * new Tag(TAG_CATEGORY_01, "카테고리 API 입니다.")
 */

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
	)
	public ResponseEntity<ResponseModel<List<FrontCategorySelectListResponse>>> list(FrontCategorySelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary ="카테고리 Page 조회", description =
		  "## Description ##\n"
		+ "카테고리 Page 조회 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_CATEGORY_002 | 카테고리 Page 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<FrontCategorySelectPageResponse>>> page(FrontCategorySelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@GetMapping("/{categorySeq}")
	@Operation(summary ="카테고리 상세 조회", description =
		  "## Description ##\n"
		+ "카테고리 상세 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_CATEGORY_003 | 카테고리 상세 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<FrontCategorySelectResponse>> detail(@PathVariable("categorySeq") Long categorySeq, @Parameter(hidden = true) FrontCategorySelectRequest req) {
		req.setCategorySeq(categorySeq);
		return RestUtil.ok(service.detail(req));
	}

	@PostMapping
	@Operation(summary ="카테고리 등록", description =
		  "## Description ##\n"
		+ "카테고리 등록 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_CATEGORY_004 | 카테고리 등록 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody FrontCategoryInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary ="카테고리 수정", description =
		  "## Description ##\n"
		+ "카테고리 수정 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_CATEGORY_005 | 카테고리 수정 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody FrontCategoryUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{categorySeq}")
	@Operation(summary ="카테고리 삭제", description =
		  "## Description ##\n"
		+ "카테고리 삭제 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_CATEGORY_006 | 카테고리 삭제 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(@PathVariable("categorySeq") Long categorySeq, @Parameter(hidden = true) FrontCategoryDeleteRequest req) {
		req.setCategorySeq(categorySeq);
		service.delete(req);
		return RestUtil.ok();
	}

}
