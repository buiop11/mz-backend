package com.matjzing.controller;

import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.*;
import com.matjzing.dto.excalendar.*;
import com.matjzing.service.FrontExcalendarService;
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
 * public static final String TAG_EXCALENDAR_01 = "02.외부캘린더"; // 번호는 채번 룰에따라
 * new Tag(TAG_EXCALENDAR_01, "외부캘린더 API 입니다.")
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
@Tag(name = TagsConfig.TAG_EXCALENDAR_01)
@RequestMapping("/api/excalendar")
public class FrontExcalendarController {
	
	private final FrontExcalendarService service;
	
	@GetMapping("/list")
	@Operation(summary ="외부캘린더 List 조회", description =
		  "## Description ##\n"
		+ "외부캘린더 List 조회 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_EXCALENDAR_001 | 외부캘린더 List 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<List<FrontExcalendarSelectListResponse>>> list(FrontExcalendarSelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary ="외부캘린더 Page 조회", description =
		  "## Description ##\n"
		+ "외부캘린더 Page 조회 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_EXCALENDAR_002 | 외부캘린더 Page 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<FrontExcalendarSelectPageResponse>>> page(FrontExcalendarSelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@GetMapping("/{excalendarSeq}")
	@Operation(summary ="외부캘린더 상세 조회", description =
		  "## Description ##\n"
		+ "외부캘린더 상세 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_EXCALENDAR_003 | 외부캘린더 상세 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<FrontExcalendarSelectResponse>> detail(@PathVariable("excalendarSeq") Long excalendarSeq, @Parameter(hidden = true) FrontExcalendarSelectRequest req) {
		req.setCalendarSeq(excalendarSeq);
		return RestUtil.ok(service.detail(req));
	}

	@PostMapping
	@Operation(summary ="외부캘린더 등록", description =
		  "## Description ##\n"
		+ "외부캘린더 등록 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_EXCALENDAR_004 | 외부캘린더 등록 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody FrontExcalendarInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary ="외부캘린더 수정", description =
		  "## Description ##\n"
		+ "외부캘린더 수정 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_EXCALENDAR_005 | 외부캘린더 수정 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody FrontExcalendarUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{excalendarSeq}")
	@Operation(summary ="외부캘린더 삭제", description =
		  "## Description ##\n"
		+ "외부캘린더 삭제 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_EXCALENDAR_006 | 외부캘린더 삭제 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(@PathVariable("excalendarSeq") Long excalendarSeq, @Parameter(hidden = true) FrontExcalendarDeleteRequest req) {
		req.setCalendarSeq(excalendarSeq);
		service.delete(req);
		return RestUtil.ok();
	}

}
