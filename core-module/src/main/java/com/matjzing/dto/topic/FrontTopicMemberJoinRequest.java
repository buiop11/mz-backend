package com.matjzing.dto.topic;

import com.matjzing.dto.common.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

/**
 * 초대/참여 플로우:
 * - TOPIC_MEMBER는 MEMBER_SEQ가 필수라, "미가입 사용자"는 먼저 가입/로그인으로 MEMBER_SEQ를 만든 뒤 호출해야 합니다.
 * - 본 API는 로그인 회원(= BaseRequest.memberSeq)을 topicSeq에 PARTICIPANT로 추가합니다.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "토픽 참여(본인) 요청")
@Alias("frontTopicMemberJoinRequest")
public class FrontTopicMemberJoinRequest extends BaseRequest {

	@Schema(hidden = true)
	private Long topicSeq;
}

