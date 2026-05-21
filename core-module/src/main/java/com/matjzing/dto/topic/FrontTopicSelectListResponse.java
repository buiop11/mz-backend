package com.matjzing.dto.topic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;


/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 목록 조회 응답 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "안건 List 조회 응답 모델")
@Alias("frontTopicSelectListResponse")
public class FrontTopicSelectListResponse {

	@Schema(description = "", example = "")
	private Long topicSeq;

	@Schema(description = "안건 등록자(OWNER) MEMBER_SEQ — TOPIC_MEMBER 기준", example = "1")
	private Long memberSeq;

	@Schema(description = "", example = "")
	private Long categorySeq;

	@Schema(description = "선정 후보 시퀀스", example = "")
	private Long candidateSeq;

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
