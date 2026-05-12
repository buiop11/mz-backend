package com.matjzing.dto.excalendar;

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
@Schema(description = "외부캘린더 List 조회 응답 모델")
@Alias("frontExcalendarSelectListResponse")
public class FrontExcalendarSelectListResponse {

	@Schema(description = "", example = "")
	private Long calendarSeq;

	@Schema(description = "", example = "")
	private Long userSeq;

	@Schema(description = "", example = "")
	private String googleCalId;

	@Schema(description = "", example = "")
	private String accessToken;

	@Schema(description = "", example = "")
	private String refreshToken;

}
