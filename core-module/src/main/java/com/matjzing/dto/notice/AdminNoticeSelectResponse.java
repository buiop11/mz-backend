package com.matjzing.dto.notice;

import com.matjzing.dto.file.FileSelectResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-04-23
 * @pname: 관리자
 * @desc: 관리자 상세 조회 응답 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "공지사항 상세 조회 응답 모델")
@Alias("adminNoticeSelectResponse")
public class AdminNoticeSelectResponse {

	@Schema(description = "파일 목록")
	private List<FileSelectResponse> fileList;

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
