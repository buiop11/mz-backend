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
 * 토픽 참여 회원 API (TOPIC_MEMBER — 기존 그룹/TOPIC_GROUPS 대체)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = TagsConfig.TAG_GROUPS_01)
@RequestMapping("/api/groups")
public class FrontGroupsController {

	private final FrontGroupsService service;

	@GetMapping("/list")
	@Operation(summary = "토픽 참여 회원 List 조회", description =
		  "## Description ##\n"
		+ "TOPIC_MEMBER 목록 조회 API 입니다.\n"
		+ "`topicSeq`로 특정 안건의 참여자만 필터할 수 있습니다.\n"
	)
	public ResponseEntity<ResponseModel<List<FrontGroupsSelectListResponse>>> list(FrontGroupsSelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary = "토픽 참여 회원 Page 조회", description =
		  "## Description ##\n"
		+ "TOPIC_MEMBER 페이지 조회 API 입니다.\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<FrontGroupsSelectPageResponse>>> page(FrontGroupsSelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@GetMapping("/{topicMemberSeq}")
	@Operation(summary = "토픽 참여 회원 상세 조회", description =
		  "## Description ##\n"
		+ "TOPIC_MEMBER 상세 API 입니다.\n"
	)
	public ResponseEntity<ResponseModel<FrontGroupsSelectResponse>> detail(
			@PathVariable("topicMemberSeq") Long topicMemberSeq,
			@Parameter(hidden = true) FrontGroupsSelectRequest req) {
		req.setTopicMemberSeq(topicMemberSeq);
		return RestUtil.ok(service.detail(req));
	}

	@PostMapping
	@Operation(summary = "토픽 참여 회원 등록", description =
		  "## Description ##\n"
		+ "TOPIC_MEMBER 등록 API 입니다 (기본 역할 PARTICIPANT).\n"
		+ "`memberSeq`는 JWT/요청으로 채웁니다.\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody FrontGroupsInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary = "토픽 참여 회원 수정", description =
		  "## Description ##\n"
		+ "TOPIC_MEMBER 역할(ROLE_TYPE) 수정 API 입니다.\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody FrontGroupsUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{topicMemberSeq}")
	@Operation(summary = "토픽 참여 회원 삭제", description =
		  "## Description ##\n"
		+ "TOPIC_MEMBER 소프트 삭제 API 입니다.\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(
			@PathVariable("topicMemberSeq") Long topicMemberSeq,
			@Parameter(hidden = true) FrontGroupsDeleteRequest req) {
		req.setTopicMemberSeq(topicMemberSeq);
		service.delete(req);
		return RestUtil.ok();
	}

}
