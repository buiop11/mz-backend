package com.matjzing.dto.topic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;


/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 페이징 조회 응답 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "안건 Page 조회 응답 모델")
@Alias("frontTopicSelectPageResponse")
public class FrontTopicSelectPageResponse {

	@Schema(description = "", example = "")
	private Long topicSeq;

	@Schema(description = "", example = "")
	private Long groupSeq;

	@Schema(description = "", example = "")
	private Long categorySeq;

	@Schema(description = "", example = "")
	private String categoryName;

	@Schema(description = "", example = "")
	private String emoji;

	@Schema(description = "", example = "")
	private String title;

	@Schema(description = "", example = "")
	private String status;

	@Schema(description = "", example = "")
	private String googleEventId;

}
