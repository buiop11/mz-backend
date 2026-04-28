package com.matjzing.dto.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.common.enumeration.FileTargetCd;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "파일 업로드 FileUploadResponse DTO")
public class FileUploadResponse {

	@Schema(description = "파일명", example = "image.png")
	private String fileOriginalName;

	@Schema(description = "파일 사이즈", example = "77738")
	private Long fileSize;

	@Schema(description = "파일경로", example = "/tmp/a2e6aef2-30a3-4e85-af6e-a164f967a904.jpg")
	private String filePath;

	@JsonIgnore
	@Schema(description = "파일전체경로", example = "/app/file/tmp/a2e6aef2-30a3-4e85-af6e-a164f967a904.jpg", hidden = true)
	private String fileFullPath;

	@Schema(description = "파일 확장자", example = "image/jpeg")
	private String fileExtensionName;

	@JsonIgnore
	@Schema(description = "파일 공개 여부", example = "false", hidden = true)
	private Boolean openFileYn;

	@JsonIgnore
	@Schema(description = "파일 사용 구분(bbs, profile 등)", example = "bbs", hidden = true)
	private FileTargetCd targetCd;

}
