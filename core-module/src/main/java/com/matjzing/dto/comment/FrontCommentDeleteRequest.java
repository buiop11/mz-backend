package com.matjzing.dto.comment;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author: 김아진
 * @date: 2026-05-14
 * @pname: 관리자
 * @desc: 관리자 삭제 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "댓글 삭제 요청 모델")
@Alias("frontCommentDeleteRequest")
public class FrontCommentDeleteRequest extends BaseRequest {

	@Schema(description = "댓글 일련번호", example = "")
	private Long commentSeq;
}
