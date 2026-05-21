package com.matjzing.dto.topic;

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
@Schema(description = "안건 등록 요청 모델")
@Alias("frontTopicInsertRequest")
public class FrontTopicInsertRequest extends BaseRequest {

	@Schema(description = "파일 목록")
    private List<FileUploadDto> fileList;

	@Schema(hidden = true)
	private Long topicSeq;

	@Schema(description = "", example = "1")
	private Long categorySeq;

	@Schema(description = "", example = "")
	private String emoji;

	@Schema(description = "", example = "")
	private String title;

	@Schema(description = "", example = "")
	private String status;

	@Schema(description = "", example = "")
	private String googleEventId;

	/*
		@NotNull(message = "필수값입니다.")
	 */
}
