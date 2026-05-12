package com.matjzing.controller;

import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.*;
import com.matjzing.dto.groups.*;
import com.matjzing.service.FrontGroupsService;
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
 * public static final String TAG_GROUPS_01 = "02.그룹"; // 번호는 채번 룰에따라
 * new Tag(TAG_GROUPS_01, "그룹 API 입니다.")
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
@Tag(name = TagsConfig.TAG_GROUPS_01)
@RequestMapping("/api/groups")
public class FrontGroupsController {
	
	private final FrontGroupsService service;
	
	@GetMapping("/list")
	@Operation(summary ="그룹 List 조회", description =
		  "## Description ##\n"
		+ "그룹 List 조회 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_GROUPS_001 | 그룹 List 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<List<FrontGroupsSelectListResponse>>> list(FrontGroupsSelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary ="그룹 Page 조회", description =
		  "## Description ##\n"
		+ "그룹 Page 조회 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_GROUPS_002 | 그룹 Page 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<FrontGroupsSelectPageResponse>>> page(FrontGroupsSelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@GetMapping("/{groupsSeq}")
	@Operation(summary ="그룹 상세 조회", description =
		  "## Description ##\n"
		+ "그룹 상세 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_GROUPS_003 | 그룹 상세 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<FrontGroupsSelectResponse>> detail(@PathVariable("groupsSeq") Long groupsSeq, @Parameter(hidden = true) FrontGroupsSelectRequest req) {
		return RestUtil.ok(service.detail(req));
	}

	@PostMapping
	@Operation(summary ="그룹 등록", description =
		  "## Description ##\n"
		+ "그룹 등록 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_GROUPS_004 | 그룹 등록 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody FrontGroupsInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary ="그룹 수정", description =
		  "## Description ##\n"
		+ "그룹 수정 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_GROUPS_005 | 그룹 수정 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody FrontGroupsUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{groupsSeq}")
	@Operation(summary ="그룹 삭제", description =
		  "## Description ##\n"
		+ "그룹 삭제 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_GROUPS_006 | 그룹 삭제 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(@PathVariable("groupsSeq") Long groupsSeq, @Parameter(hidden = true) FrontGroupsDeleteRequest req) {
		service.delete(req);
		return RestUtil.ok();
	}

}
