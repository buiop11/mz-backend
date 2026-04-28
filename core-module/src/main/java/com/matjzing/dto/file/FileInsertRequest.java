package com.matjzing.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.common.BaseRequest;
import com.matjzing.dto.common.enumeration.FileTargetCd;
import lombok.*;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("fileInsertRequest")
public class FileInsertRequest extends BaseRequest {

	@Schema(description = "첨부_파일_시퀀스", hidden = true)
	private Long attachingFileSeq;

	@Schema(description = "타겟_코드", example = "bbs", hidden = true)
	private FileTargetCd targetCd;

	@Schema(description = "타겟_시퀀스", hidden = true)
	private Long targetSeq;

	@Schema(description = "파일_원본_명", example = "image.png", hidden = true)
	private String fileOriginalName;

	@Schema(description = "파일_경로", example = "/tmp/a2e6aef2-30a3-4e85-af6e-a164f967a904.jpg", hidden = true)
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

	@Schema(description = "Admin 여부", example = "false", hidden = true)
	private boolean adminYn;

	@Schema(description = "이미지 src", example = "<img src='/temp/d6157d28-e394-4b10-98a6-46271da662c4.png'>", hidden = true)
	private String imgSrc;

}