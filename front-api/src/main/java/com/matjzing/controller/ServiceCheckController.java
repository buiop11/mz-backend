package com.matjzing.controller;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.CacheCom02;
import com.matjzing.dto.common.EmptyResponse;
import com.matjzing.dto.common.ResponseModel;
import com.matjzing.dto.servicecheck.FrontServiceCheckInsertRequest;
import com.matjzing.service.FrontServiceCheckService;
import com.matjzing.util.RestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = TagsConfig.TAG_UTIL_02)
@RequestMapping("/api/swagger/servicecheck")
public class ServiceCheckController {

	private final FrontServiceCheckService service;

	@PostMapping()
	@Operation(summary ="시스템 점검 등록", description =
		  "## Description ##\n"
		+ "시스템 점검 등록 API 입니다\n"
	)
	@Parameters({
			@Parameter(name = "pw", required = true, in = ParameterIn.HEADER) })
	public ResponseEntity<ResponseModel<EmptyResponse>> insertServiceCheck(@Valid @RequestBody FrontServiceCheckInsertRequest req, HttpServletRequest request) {
		service.insertServiceCheck(req, request.getHeader("pw"));
		return RestUtil.ok();
	}

	@GetMapping
	@Operation(summary ="시스템 점검 조회", description =
		  "## Description ##\n"
		+ "시스템 점검 조회 API 입니다\n"
	)
	public ResponseEntity<ResponseModel<CacheCom02>> getServiceCheck() {
		return RestUtil.ok(service.getServiceCheckData());
	}

}
