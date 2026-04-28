package com.matjzing.dto.notice;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;


/**
 * @author: 김아진
 * @date: 2026-04-23
 * @pname: 관리자
 * @desc: 관리자 페이징 조회 응답 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "공지사항 Page 조회 응답 모델")
@Alias("adminNoticeSelectPageResponse")
public class AdminNoticeSelectPageResponse {

	@Schema(description = "_", example = "")
	private Long noticeSeq;

	@Schema(description = "", example = "")
	private String title;

	@Schema(description = "", example = "")
	private String content;

	@Schema(description = "_", example = "2026-04-23T15:31:37")
	private LocalDateTime displayDt;

	@Schema(description = "_", example = "")
	private boolean displayYn;

	@Schema(description = "_", example = "")
	private boolean fixedYn;

	@Schema(description = "", example = "")
	private Integer viewCount;

}
