package com.matjzing.dto.comment;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.file.FileUploadDto;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-05-14
 * @pname: 관리자
 * @desc: 관리자 수정 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "댓글 수정 요청 모델")
@Alias("frontCommentUpdateRequest")
public class FrontCommentUpdateRequest extends BaseRequest {

	@Schema(description = "파일 목록")
	private List<FileUploadDto> fileList;

	@Schema(description = "댓글 일련번호", example = "")
	private Long commentSeq;

	@Schema(description = "후보자(대상) 일련번호", example = "")
	private Long candidateSeq;

	@Schema(description = "댓글 내용", example = "")
	private String content;

	/*
		@NotNull(message = "필수값입니다.")
	 */
}

