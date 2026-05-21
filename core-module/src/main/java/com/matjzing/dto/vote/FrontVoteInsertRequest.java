package com.matjzing.dto.vote;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.file.FileUploadDto;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 등록 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "투표 등록 요청 모델")
@Alias("frontVoteInsertRequest")
public class FrontVoteInsertRequest extends BaseRequest {

	@Schema(description = "파일 목록")
    private List<FileUploadDto> fileList;

	@Schema(description = "", example = "")
	private Long voteSeq;

	@Schema(description = "", example = "")
	private Long topicSeq;

	@Schema(description = "", example = "")
	private Long candidateSeq;

	@Schema(description = "", example = "")
	private String comment;

	/*
		@NotNull(message = "필수값입니다.")
	 */
}
