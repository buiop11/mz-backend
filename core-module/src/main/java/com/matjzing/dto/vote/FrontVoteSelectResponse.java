package com.matjzing.dto.vote;

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
@Schema(description = "투표 상세 조회 응답 모델")
@Alias("frontVoteSelectResponse")
public class FrontVoteSelectResponse {

	@Schema(description = "파일 목록")
	private List<FileSelectResponse> fileList;

	@Schema(description = "", example = "")
	private Long voteSeq;

	@Schema(description = "", example = "")
	private Long topicSeq;

	@Schema(description = "", example = "")
	private Long candidateSeq;

	@Schema(description = "투표 회원 시퀀스", example = "")
	private Long memberSeq;

	@Schema(description = "", example = "")
	private String comment;

}
