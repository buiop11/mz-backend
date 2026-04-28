package com.matjzing.dto.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.common.BaseRequest;
import com.matjzing.dto.common.enumeration.FileTargetCd;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "파일 업로드 FileUploadDto DTO")
public class FileUploadDto extends BaseRequest {

	@Schema(description = "파일명", example = "image.png")
	private String fileOriginalName;

	@Schema(description = "파일 사이즈", example = "77738")
	private Long fileSize;

	@Schema(description = "파일경로", example = "/tmp/a2e6aef2-30a3-4e85-af6e-a164f967a904.jpg")
	private String filePath;

	@Schema(description = "파일 확장자", example = "image/jpeg")
	private String fileExtensionName;

	@Schema(description = "수정만 사용: 삭제 여부", example = "false")
	private Boolean delYn=false;

	@Schema(description = "수정시만 사용 파일 일련번호", example = "1")
	private Long attachingFileSeq;

	@JsonIgnore
	@Schema(description = "파일전체경로", example = "/app/file/tmp/a2e6aef2-30a3-4e85-af6e-a164f967a904.jpg",  hidden = true)
	private String fileFullPath;

	@JsonIgnore
	@Schema(description = "파일 공개 여부", example = "false", hidden = true)
	private Boolean openFileYn;

	@JsonIgnore
	@Schema(description = "파일 사용 구분(bbs, profile 등)", example = "bbs", hidden = true)
	private FileTargetCd targetCd;

}
