package com.matjzing.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.EPageInfo;
import com.matjzing.dto.common.EmptyResponse;
import com.matjzing.dto.common.ResponseModel;
import com.matjzing.dto.swagger.*;
import com.matjzing.service.SwaggerUserService;
import com.matjzing.util.RestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = TagsConfig.TAG_SWAGGER_MEMBER_01)
@RequestMapping("/api/swagger/swaggeruser")
public class SwaggerUserController {

    private final SwaggerUserService service;

    @GetMapping
    @Operation(summary ="SWAGGER_회원 Page 조회", description =
          "## Description ##\n"
        + "SWAGGER_회원 Page 조회 API 입니다\n"
    )
    public ResponseEntity<ResponseModel<EPageInfo<SwaggerUserSelectPageResponse>>> page(SwaggerUserSelectPageRequest req) {
        return RestUtil.ok(service.page(req));
    }

    @GetMapping("/{swaggerUserSeq}")
    @Operation(summary ="SWAGGER_회원 상세 조회", description =
          "## Description ##\n"
        + "SWAGGER_회원 상세 API 입니다\n"
    )
    public ResponseEntity<ResponseModel<SwaggerUserSelectResponse>> detail(@PathVariable("swaggerUserSeq") Long swaggerUserSeq, @Parameter(hidden = true) SwaggerUserSelectRequest req) {
        return RestUtil.ok(service.detail(req));
    }

    @PostMapping
    @Operation(summary ="SWAGGER_회원 등록", description =
          "## Description ##\n"
        + "SWAGGER_회원 등록 API 입니다\n"
    )
    public ResponseEntity<ResponseModel<EmptyResponse>> insert(@Valid @RequestBody SwaggerUserInsertRequest req) {
        service.insert(req);
        return RestUtil.ok();
    }

    @PutMapping
    @Operation(summary ="SWAGGER_회원 수정", description =
          "## Description ##\n"
        + "SWAGGER_회원 수정 API 입니다\n"
    )
    public ResponseEntity<ResponseModel<EmptyResponse>> update(@Valid @RequestBody SwaggerUserUpdateRequest req) {
        service.update(req);
        return RestUtil.ok();
    }

    @DeleteMapping("/{swaggerUserSeq}")
    @Operation(summary ="SWAGGER_회원 삭제", description =
          "## Description ##\n"
        + "SWAGGER_회원 삭제 API 입니다\n"
    )
    public ResponseEntity<ResponseModel<EmptyResponse>> delete(@PathVariable("swaggerUserSeq") Long swaggerUserSeq, @Parameter(hidden = true) SwaggerUserDeleteRequest req) {
        service.delete(req);
        return RestUtil.ok();
    }

}
