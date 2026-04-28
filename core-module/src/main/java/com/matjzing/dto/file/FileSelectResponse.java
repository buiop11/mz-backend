package com.matjzing.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("fileSelectResponse")
public class FileSelectResponse {

	@Schema(description = "파일 일련번호", example = "1")
	private Long attachingFileSeq;

	@Schema(description = "대상 게시물 일련번호", example = "1")
	private Long targetSeq;

	@Schema(description = "파일명", example = "image.png")
	private String fileOriginalName;

	@Schema(description = "파일경로", example = "/tmp/a2e6aef2-30a3-4e85-af6e-a164f967a904.jpg")
	private String filePath;

	@Schema(description = "파일 사이즈", example = "77738")
	private Long fileSize;

	@Schema(description = "삭제 여부", example = "false")
	private boolean delYn;

}
