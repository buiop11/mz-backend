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
		+ "로그인 회원이 TOPIC_MEMBER(OWNER 또는 PARTICIPANT)로 포함된 안건만 조회됩니다.\n"
	)
	public ResponseEntity<ResponseModel<List<FrontTopicSelectListResponse>>> list(FrontTopicSelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary ="안건 Page 조회", description =
		  "## Description ##\n"
		+ "안건 Page 조회 API 입니다\n"
		+ "로그인 회원이 TOPIC_MEMBER로 포함된 안건만 조회됩니다.\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<FrontTopicSelectPageResponse>>> page(FrontTopicSelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@GetMapping("/{topicSeq}")
	@Operation(summary ="안건 상세 조회", description =
		  "## Description ##\n"
		+ "안건 상세 API 입니다\n"
		+ "TOPIC_MEMBER에 포함된 회원만 조회할 수 있습니다.\n"
	)
	public ResponseEntity<ResponseModel<FrontTopicSelectResponse>> detail(@PathVariable("topicSeq") Long topicSeq, @Parameter(hidden = true) FrontTopicSelectRequest req) {
		req.setTopicSeq(topicSeq);
		return RestUtil.ok(service.detail(req));
	}

	@PostMapping
	@Operation(summary ="안건 등록", description =
		  "## Description ##\n"
		+ "안건 등록 API 입니다\n"
		+ "`memberSeq`: JWT/요청으로 감사 필드(REGISTER_SEQ 등) 채움. 안건 회원 관계는 TOPIC_MEMBER(OWNER/PARTICIPANT)만 사용.\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody FrontTopicInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PostMapping("/{topicSeq}/members/join")
	@Operation(summary ="토픽 참여(본인)", description =
		  "## Description ##\n"
		+ "초대 링크 등으로 topicSeq를 받은 사용자가, 로그인 후 본인을 TOPIC_MEMBER(PARTICIPANT)로 참여시키는 API 입니다.\n"
		+ "TOPIC_MEMBER는 MEMBER_SEQ가 필수이므로, 미가입 사용자는 먼저 회원가입/로그인으로 MEMBER_SEQ를 만든 뒤 호출해야 합니다.\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> join(@PathVariable("topicSeq") Long topicSeq, @Parameter(hidden = true) FrontTopicMemberJoinRequest req) {
		req.setTopicSeq(topicSeq);
		service.join(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary ="안건 수정", description =
		  "## Description ##\n"
		+ "안건 수정 API 입니다\n"
		+ "수정은 TOPIC_MEMBER에서 ROLE_TYPE=OWNER 인 회원만 가능. `memberSeq`는 JWT/요청으로 감사 필드에 반영.\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody FrontTopicUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{topicSeq}")
	@Operation(summary ="안건 삭제", description =
		  "## Description ##\n"
		+ "안건 삭제 API 입니다\n"
		+ "삭제(소프트)는 OWNER만. `memberSeq`는 JWT/요청으로 감사 필드에 반영.\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(@PathVariable("topicSeq") Long topicSeq, @Parameter(hidden = true) FrontTopicDeleteRequest req) {
		req.setTopicSeq(topicSeq);
		service.delete(req);
		return RestUtil.ok();
	}

}
