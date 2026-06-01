package com.matjzing.dto.candidate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;


/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 페이징 조회 응답 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "후보 Page 조회 응답 모델")
@Alias("frontCandidateSelectPageResponse")
public class FrontCandidateSelectPageResponse {

	@Schema(description = "", example = "")
	private Long candidateSeq;

	@Schema(description = "", example = "")
	private Long topicSeq;

	@Schema(description = "제안 회원 시퀀스", example = "")
	private Long memberSeq;

	@Schema(description = "", example = "")
	private String name;

	@Schema(description = "", example = "")
	private String info;

	@Schema(description = "금액", example = "")
	private Integer price;

	@Schema(description = "", example = "")
	private String imageUrl;

	@Schema(description = "", example = "")
	private String linkUrl;

	@Schema(description = "", example = "")
	private boolean isFixed;

	@Schema(description = "일자", example = "")
	private LocalDateTime pickDate;

}
