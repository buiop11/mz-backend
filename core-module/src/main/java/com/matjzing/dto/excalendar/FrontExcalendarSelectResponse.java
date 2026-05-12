package com.matjzing.dto.excalendar;

import com.matjzing.dto.file.FileSelectResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 상세 조회 응답 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "외부캘린더 상세 조회 응답 모델")
@Alias("frontExcalendarSelectResponse")
public class FrontExcalendarSelectResponse {

	@Schema(description = "파일 목록")
	private List<FileSelectResponse> fileList;

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
