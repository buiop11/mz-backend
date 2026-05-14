package com.matjzing.dto.candidate;

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
@Schema(description = "후보 List 조회 응답 모델")
@Alias("frontCandidateSelectListResponse")
public class FrontCandidateSelectListResponse {

	@Schema(description = "", example = "")
	private Long candidateSeq;

	@Schema(description = "", example = "")
	private Long topicSeq;

	@Schema(description = "", example = "")
	private String name;

	@Schema(description = "", example = "")
	private String info;

	@Schema(description = "", example = "")
	private Integer price;

	@Schema(description = "", example = "")
	private String imageUrl;

	@Schema(description = "", example = "")
	private String linkUrl;

	@Schema(description = "", example = "")
	private boolean isFixed;

}
