package com.matjzing.controller;

import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.*;
import com.matjzing.dto.topic.*;
import com.matjzing.service.FrontTopicService;
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
 * public static final String TAG_TOPIC_01 = "02.안건"; // 번호는 채번 룰에따라
 * new Tag(TAG_TOPIC_01, "안건 API 입니다.")
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
@Tag(name = TagsConfig.TAG_TOPIC_01)
@RequestMapping("/api/topic")
public class FrontTopicController {
	
	private final FrontTopicService service;
	
	@GetMapping("/list")
	@Operation(summary ="안건 List 조회", description =
		  "## Description ##\n"
		+ "안건 List 조회 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<List<FrontTopicSelectListResponse>>> list(FrontTopicSelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary ="안건 Page 조회", description =
		  "## Description ##\n"
		+ "안건 Page 조회 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<FrontTopicSelectPageResponse>>> page(FrontTopicSelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@GetMapping("/{topicSeq}")
	@Operation(summary ="안건 상세 조회", description =
		  "## Description ##\n"
		+ "안건 상세 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<FrontTopicSelectResponse>> detail(@PathVariable("topicSeq") Long topicSeq, @Parameter(hidden = true) FrontTopicSelectRequest req) {
		return RestUtil.ok(service.detail(req));
	}

	@PostMapping
	@Operation(summary ="안건 등록", description =
		  "## Description ##\n"
		+ "안건 등록 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody FrontTopicInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary ="안건 수정", description =
		  "## Description ##\n"
		+ "안건 수정 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody FrontTopicUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{topicSeq}")
	@Operation(summary ="안건 삭제", description =
		  "## Description ##\n"
		+ "안건 삭제 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(@PathVariable("topicSeq") Long topicSeq, @Parameter(hidden = true) FrontTopicDeleteRequest req) {
		service.delete(req);
		return RestUtil.ok();
	}

}
