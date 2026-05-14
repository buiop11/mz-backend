package com.matjzing.dto.candidate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author: 김아진
 * @date: 2026-05-14
 * @pname: 관리자
 * @desc: 후보 개수 조회 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "후보 개수 조회 요청 모델")
@Alias("frontCandidateCountRequest")
public class FrontCandidateCountRequest {

	@Schema(description = "안건 시퀀스", example = "1")
	@NotNull(message = "안건 일련번호는 필수입니다.")
	private Long topicSeq;
}
