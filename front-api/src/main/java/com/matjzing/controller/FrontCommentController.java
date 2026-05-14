package com.matjzing.controller;

import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.*;
import com.matjzing.dto.comment.*;
import com.matjzing.service.FrontCommentService;
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
 * public static final String TAG_COMMENT_01 = "02.댓글"; // 번호는 채번 룰에따라
 * new Tag(TAG_COMMENT_01, "댓글 API 입니다.")
 */

/**
 * @author: 김아진
 * @date: 2026-05-14
 * @pname: 관리자
 * @desc: 관리자 컨트롤러 작성
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = TagsConfig.TAG_COMMENT_01)
@RequestMapping("/api/comment")
public class FrontCommentController {
	
	private final FrontCommentService service;
	
	@GetMapping("/list")
	@Operation(summary ="댓글 List 조회", description =
		  "## Description ##\n"
		+ "댓글 List 조회 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<List<FrontCommentSelectListResponse>>> list(FrontCommentSelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary ="댓글 Page 조회", description =
		  "## Description ##\n"
		+ "댓글 Page 조회 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<FrontCommentSelectPageResponse>>> page(FrontCommentSelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@PostMapping
	@Operation(summary ="댓글 등록", description =
		  "## Description ##\n"
		+ "댓글 등록 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody FrontCommentInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary ="댓글 수정", description =
		  "## Description ##\n"
		+ "댓글 수정 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody FrontCommentUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{commentSeq}")
	@Operation(summary ="댓글 삭제", description =
		  "## Description ##\n"
		+ "댓글 삭제 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(@PathVariable("commentSeq") Long commentSeq, @Parameter(hidden = true) FrontCommentDeleteRequest req) {
		service.delete(req);
		return RestUtil.ok();
	}

}
