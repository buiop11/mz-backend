package com.matjzing.dto.topic;

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
@Schema(description = "안건 삭제 요청 모델")
@Alias("frontTopicDeleteRequest")
public class FrontTopicDeleteRequest extends BaseRequest {

	@Schema(description = "", example = "")
	private Long topicSeq;
}
