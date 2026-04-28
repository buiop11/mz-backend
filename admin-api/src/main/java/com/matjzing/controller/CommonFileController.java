package com.matjzing.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.matjzing.config.TagsConfig;
import com.matjzing.dto.common.ResponseModel;
import com.matjzing.dto.file.FileInsertRequest;
import com.matjzing.dto.file.FileUploadRequest;
import com.matjzing.dto.file.FileUploadResponse;
import com.matjzing.service.CommonFileService;
import com.matjzing.dto.common.enumeration.ErrorCode;
import com.matjzing.util.RestUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Tag(name = TagsConfig.TAG_COM_01)
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class CommonFileController {

	private final CommonFileService commonFileService;

	@Operation(summary ="멀티 파일 업로드", description =
		  "## Description ##\n"
		+ "멀티 파일 업로드 API 입니다\n"
	)
	@PostMapping(value="/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseModel<List<FileUploadResponse>>> fileUpload(@Valid FileUploadRequest fileUploadRequest) throws Exception {
		List<FileUploadResponse> list = new ArrayList<>();
		for (MultipartFile file :fileUploadRequest.getFiles()) {
			list.add(commonFileService.saveTemp(file));
		}

		if (!list.isEmpty()) {
			commonFileService.uploadS3(list);
		}

		return RestUtil.ok(list);
	}

	@Operation(summary ="단건 파일 업로드", description =
		  "## Description ##\n"
		+ "단건 파일 업로드 API 입니다\n"
	)
	@PostMapping(value="/upload", produces = "application/json; charset=utf8")
	public ResponseEntity<ResponseModel<FileUploadResponse>> fileUploads(@RequestParam("file") MultipartFile file) throws Exception {
		FileUploadResponse fileUploadResponse = commonFileService.saveTemp(file);
		if (null != fileUploadResponse) {
			commonFileService.uploadS3(fileUploadResponse);
			return RestUtil.ok(fileUploadResponse);
		}
		return RestUtil.error();
	}

	@Operation(summary ="파일 다운로드", description =
		  "## Description ##\n"
		+ "파일 다운로드 API 입니다\n"
	)
	@GetMapping(value = "/download/{fileSeq}")
	public ResponseEntity<?> downloadFile(@PathVariable("attachingFileSeq") Long attachingFileSeq, @Parameter(hidden = true) FileInsertRequest dto) {
		try {
			dto.setAdminYn(false);
			return commonFileService.downloadFile(dto);
		}catch (Exception e) {
			return RestUtil.error(ErrorCode.ERR404_001);
		}
	}

}
