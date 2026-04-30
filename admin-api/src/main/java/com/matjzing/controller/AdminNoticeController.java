package com.matjzing.controller;

import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.*;
import com.matjzing.dto.notice.*;
import com.matjzing.service.AdminNoticeService;
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
 * public static final String TAG_NOTICE_01 = "02.공지사항"; // 번호는 채번 룰에따라
 * new Tag(TAG_NOTICE_01, "공지사항 API 입니다.")
 */

/**
 * @author: 김아진
 * @date: 2026-04-23
 * @pname: 관리자
 * @desc: 관리자 컨트롤러 작성
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = TagsConfig.TAG_NOTICE_01)
@RequestMapping("/api/v1/notice")
public class AdminNoticeController {
	
	private final AdminNoticeService service;
	
	@GetMapping("/list")
	@Operation(summary ="공지사항 List 조회", description =
		  "## Description ##\n"
		+ "공지사항 List 조회 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_NOTICE_001 | 공지사항 List 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<List<AdminNoticeSelectListResponse>>> list(AdminNoticeSelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary ="공지사항 Page 조회", description =
		  "## Description ##\n"
		+ "공지사항 Page 조회 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_NOTICE_002 | 공지사항 Page 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<AdminNoticeSelectPageResponse>>> page(AdminNoticeSelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@GetMapping("/{noticeSeq}")
	@Operation(summary ="공지사항 상세 조회", description =
		  "## Description ##\n"
		+ "공지사항 상세 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_NOTICE_003 | 공지사항 상세 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<AdminNoticeSelectResponse>> detail(
			@PathVariable("noticeSeq") Long noticeSeq, @Parameter(hidden = true) AdminNoticeSelectRequest req) {
		return RestUtil.ok(service.detail(req));
	}

	@PostMapping
	@Operation(summary ="공지사항 등록", description =
		  "## Description ##\n"
		+ "공지사항 등록 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_NOTICE_004 | 공지사항 등록 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody AdminNoticeInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary ="공지사항 수정", description =
		  "## Description ##\n"
		+ "공지사항 수정 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_NOTICE_005 | 공지사항 수정 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody AdminNoticeUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{noticeSeq}")
	@Operation(summary ="공지사항 삭제", description =
		  "## Description ##\n"
		+ "공지사항 삭제 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_NOTICE_006 | 공지사항 삭제 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(@PathVariable("noticeSeq") Long noticeSeq, @Parameter(hidden = true) AdminNoticeDeleteRequest req) {
		service.delete(req);
		return RestUtil.ok();
	}

}
