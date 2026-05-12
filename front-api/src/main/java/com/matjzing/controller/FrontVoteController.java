package com.matjzing.controller;

import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.*;
import com.matjzing.dto.vote.*;
import com.matjzing.service.FrontVoteService;
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
 * public static final String TAG_VOTE_01 = "02.투표"; // 번호는 채번 룰에따라
 * new Tag(TAG_VOTE_01, "투표 API 입니다.")
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
@Tag(name = TagsConfig.TAG_VOTE_01)
@RequestMapping("/api/vote")
public class FrontVoteController {
	
	private final FrontVoteService service;
	
	@GetMapping("/list")
	@Operation(summary ="투표 List 조회", description =
		  "## Description ##\n"
		+ "투표 List 조회 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_VOTE_001 | 투표 List 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<List<FrontVoteSelectListResponse>>> list(FrontVoteSelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary ="투표 Page 조회", description =
		  "## Description ##\n"
		+ "투표 Page 조회 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_VOTE_002 | 투표 Page 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<FrontVoteSelectPageResponse>>> page(FrontVoteSelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@GetMapping("/{voteSeq}")
	@Operation(summary ="투표 상세 조회", description =
		  "## Description ##\n"
		+ "투표 상세 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_VOTE_003 | 투표 상세 조회 실패\n"
	)
	public ResponseEntity<ResponseModel<FrontVoteSelectResponse>> detail(@PathVariable("voteSeq") Long voteSeq, @Parameter(hidden = true) FrontVoteSelectRequest req) {
		return RestUtil.ok(service.detail(req));
	}

	@PostMapping
	@Operation(summary ="투표 등록", description =
		  "## Description ##\n"
		+ "투표 등록 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_VOTE_004 | 투표 등록 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody FrontVoteInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary ="투표 수정", description =
		  "## Description ##\n"
		+ "투표 수정 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_VOTE_005 | 투표 수정 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody FrontVoteUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{voteSeq}")
	@Operation(summary ="투표 삭제", description =
		  "## Description ##\n"
		+ "투표 삭제 API 입니다\n"
//		+ "## 에러코드 ##\n"
//		+ "코드|설명\n"
//		+ "-|-\n"
//		+ "ERR_VOTE_006 | 투표 삭제 실패\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(@PathVariable("voteSeq") Long voteSeq, @Parameter(hidden = true) FrontVoteDeleteRequest req) {
		service.delete(req);
		return RestUtil.ok();
	}

}
