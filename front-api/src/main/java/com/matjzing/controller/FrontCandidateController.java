package com.matjzing.controller;

import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.*;
import com.matjzing.dto.candidate.*;
import com.matjzing.service.FrontCandidateService;
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
 * public static final String TAG_CANDIDATE_01 = "02.후보"; // 번호는 채번 룰에따라
 * new Tag(TAG_CANDIDATE_01, "후보 API 입니다.")
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
@Tag(name = TagsConfig.TAG_CANDIDATE_01)
@RequestMapping("/api/candidate")
public class FrontCandidateController {
	
	private final FrontCandidateService service;
	
	@GetMapping("/list")
	@Operation(summary ="후보 List 조회", description =
		  "## Description ##\n"
		+ "후보 List 조회 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<List<FrontCandidateSelectListResponse>>> list(FrontCandidateSelectListRequest req) {
		return RestUtil.ok(service.list(req));
	}

	@GetMapping
	@Operation(summary ="후보 Page 조회", description =
		  "## Description ##\n"
		+ "후보 Page 조회 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EPageInfo<FrontCandidateSelectPageResponse>>> page(FrontCandidateSelectPageRequest req) {
		return RestUtil.ok(service.page(req));
	}

	@GetMapping("/count")
	@Operation(summary ="안건별 후보 개수 조회", description =
		  "## Description ##\n"
		+ "topicSeq 별 후보 개수 조회 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<FrontCandidateCountResponse>> count(@Valid FrontCandidateCountRequest req) {
		return RestUtil.ok(service.count(req));
	}

	@GetMapping("/{candidateSeq}")
	@Operation(summary ="후보 상세 조회", description =
		  "## Description ##\n"
		+ "후보 상세 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<FrontCandidateSelectResponse>> detail(@PathVariable("candidateSeq") Long candidateSeq, @Parameter(hidden = true) FrontCandidateSelectRequest req) {
		req.setCandidateSeq(candidateSeq);
		return RestUtil.ok(service.detail(req));
	}

	@PostMapping
	@Operation(summary ="후보 등록", description =
		  "## Description ##\n"
		+ "후보 등록 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody FrontCandidateInsertRequest req) {
		service.insert(req);
		return RestUtil.ok();
	}

	@PutMapping
	@Operation(summary ="후보 수정", description =
		  "## Description ##\n"
		+ "후보 수정 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody FrontCandidateUpdateRequest req) {
		service.update(req);
		return RestUtil.ok();
	}

	@DeleteMapping("/{candidateSeq}")
	@Operation(summary ="후보 삭제", description =
		  "## Description ##\n"
		+ "후보 삭제 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<EmptyResponse>> delete(@PathVariable("candidateSeq") Long candidateSeq, @Parameter(hidden = true) FrontCandidateDeleteRequest req) {
		req.setCandidateSeq(candidateSeq);
		service.delete(req);
		return RestUtil.ok();
	}

}
