package com.matjzing.dto.category;

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
@Schema(description = "카테고리 Page 조회 응답 모델")
@Alias("frontCategorySelectPageResponse")
public class FrontCategorySelectPageResponse {

	@Schema(description = "", example = "")
	private Long categorySeq;

	@Schema(description = "회원 시퀀스 (0=공통 기본 카테고리)", example = "0")
	private Long memberSeq;

	@Schema(description = "공통 기본 카테고리 여부 (MEMBER_SEQ=0)", example = "true")
	private Boolean defaultCategory;

	@Schema(description = "", example = "")
	private String name;

	@Schema(description = "", example = "")
	private String emoji;

}
