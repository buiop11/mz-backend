package com.matjzing.dto.candidate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author: 김아진
 * @date: 2026-05-14
 * @pname: 관리자
 * @desc: 후보 개수 조회 응답 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "후보 개수 조회 응답 모델")
@Alias("frontCandidateCountResponse")
public class FrontCandidateCountResponse {

	@Schema(description = "후보 개수", example = "5")
	private Long count;
}
