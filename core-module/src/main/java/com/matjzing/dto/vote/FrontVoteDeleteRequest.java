package com.matjzing.dto.vote;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 삭제 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "투표 삭제 요청 모델")
@Alias("frontVoteDeleteRequest")
public class FrontVoteDeleteRequest extends BaseRequest {

	@Schema(description = "", example = "")
	private Long voteSeq;
}
