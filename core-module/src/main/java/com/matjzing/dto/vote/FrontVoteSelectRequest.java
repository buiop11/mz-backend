package com.matjzing.dto.vote;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 상세 조회 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "투표 상세 조회 요청 모델")
@Alias("frontVoteSelectRequest")
public class FrontVoteSelectRequest {

	@Schema(description = "", example = "")
	private Long voteSeq;
	/*
		@NotNull(message = "필수값입니다.")
	 */
}
