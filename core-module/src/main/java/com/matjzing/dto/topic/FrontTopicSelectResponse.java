package com.matjzing.dto.topic;

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
@Schema(description = "안건 상세 조회 응답 모델")
@Alias("frontTopicSelectResponse")
public class FrontTopicSelectResponse {

	@Schema(description = "파일 목록")
	private List<FileSelectResponse> fileList;

	@Schema(description = "", example = "")
	private Long topicSeq;

	@Schema(description = "안건 등록자(OWNER) MEMBER_SEQ — TOPIC_MEMBER 기준", example = "1")
	private Long memberSeq;

	@Schema(description = "", example = "")
	private Long categorySeq;

	@Schema(description = "선정 후보 시퀀스", example = "")
	private Long candidateSeq;

	@Schema(description = "", example = "")
	private String categoryName;

	@Schema(description = "", example = "")
	private String emoji;

	@Schema(description = "", example = "")
	private String title;

	@Schema(description = "상태 (VOTING | PICK)", example = "VOTING")
	private String status;

	@Schema(description = "PICK 확정 여부 (STATUS='PICK')", example = "false")
	private Boolean picked;

	@Schema(description = "", example = "")
	private String googleEventId;

}
