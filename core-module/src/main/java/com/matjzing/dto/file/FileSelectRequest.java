package com.matjzing.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.common.BaseRequest;
import com.matjzing.dto.common.enumeration.FileTargetCd;
import lombok.*;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = false)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("fileSelectRequest")
public class FileSelectRequest extends BaseRequest {

	@Schema(description = "파일 일련번호", example = "1", hidden = true)
	private Long fileSeq;

	@Schema(description = "파일 사용 구분(bbs, profile 등)", example = "bbs", hidden = true)
	private FileTargetCd targetCd;

	@Schema(description = "대상 게시물 일련번호", example = "1", hidden = true)
	private Long targetSeq;

	@Schema(description = "파일명", example = "image.png", hidden = true)
	private String fileName;

	@Schema(description = "파일경로", example = "/tmp/a2e6aef2-30a3-4e85-af6e-a164f967a904.jpg", hidden = true)
	private String filePath;

	@Schema(description = "파일 사이즈", example = "77738", hidden = true)
	private Long fileSize;

	@Schema(description = "파일 확장자", example = "image/jpeg", hidden = true)
	private String fileExtensionName;

	@Schema(description = "사용 여부", example = "false", hidden = true)
	private Boolean useYn;

	@Schema(description = "삭제 여부", example = "false", hidden = true)
	private Boolean delYn;

	@Schema(description = "파일 공개 여부", example = "false", hidden = true)
	private Boolean openFileYn;

	@Schema(description = "file url prefix", example = "https://dev-static.domain.co.kr", hidden = true)
	private String fileUrlPrefix;

}
