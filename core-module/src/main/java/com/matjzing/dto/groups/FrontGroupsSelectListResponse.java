package com.matjzing.dto.groups;

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
@Schema(description = "그룹 List 조회 응답 모델")
@Alias("frontGroupsSelectListResponse")
public class FrontGroupsSelectListResponse {

	@Schema(description = "", example = "")
	private Long groupSeq;

	@Schema(description = "", example = "")
	private String name;

	@Schema(description = "", example = "")
	private String inviteCode;

}
