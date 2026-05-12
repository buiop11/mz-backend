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
@Alias("adminCategorySelectPageResponse")
public class AdminCategorySelectPageResponse {

	@Schema(description = "", example = "")
	private Long categorySeq;

	@Schema(description = "", example = "")
	private String name;

	@Schema(description = "", example = "")
	private String iconUrl;

}
