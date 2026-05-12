package com.matjzing.dto.vote;

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
@Schema(description = "투표 List 조회 응답 모델")
@Alias("frontVoteSelectListResponse")
public class FrontVoteSelectListResponse {

	@Schema(description = "", example = "")
	private Long voteSeq;

	@Schema(description = "", example = "")
	private Long topicSeq;

	@Schema(description = "", example = "")
	private Long candidateSeq;

	@Schema(description = "", example = "")
	private Long userSeq;

	@Schema(description = "", example = "")
	private String comment;

}
