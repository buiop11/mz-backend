package com.matjzing.dto.comment;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.file.FileUploadDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-05-14
 * @pname: 관리자
 * @desc: 관리자 등록 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "댓글 등록 요청 모델")
@Alias("frontCommentInsertRequest")
public class FrontCommentInsertRequest extends BaseRequest {

	@Hidden
	@Schema(description = "", example = "")
	private Long commentSeq;

	@Schema(description = "후보자(대상) 일련번호", example = "1")
	@NotNull(message = "필수값입니다.")
	private Long candidateSeq;

	@Schema(description = "댓글 내용", example = "")
	@NotNull(message = "필수값입니다.")
	private String content;

}
