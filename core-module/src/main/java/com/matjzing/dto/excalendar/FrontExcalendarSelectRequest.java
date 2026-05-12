package com.matjzing.dto.excalendar;

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
@Schema(description = "외부캘린더 상세 조회 요청 모델")
@Alias("frontExcalendarSelectRequest")
public class FrontExcalendarSelectRequest {

	@Schema(description = "", example = "")
	private Long calendarSeq;
	/*
		@NotNull(message = "필수값입니다.")
	 */
}
