package com.matjzing.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;


/**
 * @author: 김아진
 * @date: 2026-05-14
 * @pname: 관리자
 * @desc: 관리자 목록 조회 응답 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "댓글 List 조회 응답 모델")
@Alias("frontCommentSelectListResponse")
public class FrontCommentSelectListResponse {

	@Schema(description = "댓글 일련번호", example = "")
	private Long commentSeq;

	@Schema(description = "후보자(대상) 일련번호", example = "")
	private Long candidateSeq;

	@Schema(description = "댓글 내용", example = "")
	private String content;

}
