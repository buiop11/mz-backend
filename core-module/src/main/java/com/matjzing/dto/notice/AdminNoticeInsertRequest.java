package com.matjzing.dto.notice;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.file.FileUploadDto;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-04-23
 * @pname: 관리자
 * @desc: 관리자 등록 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "공지사항 등록 요청 모델")
@Alias("adminNoticeInsertRequest")
public class AdminNoticeInsertRequest extends BaseRequest {

	@Schema(description = "파일 목록")
    private List<FileUploadDto> fileList;

	@Schema(description = "공지사항 시퀀스", example = "1")
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

	@Schema(description = "", example = "0")
	private Integer viewCount;

}
