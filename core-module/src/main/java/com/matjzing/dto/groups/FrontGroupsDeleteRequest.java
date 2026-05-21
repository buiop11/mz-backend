package com.matjzing.dto.groups;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * 토픽 참여 회원(TOPIC_MEMBER) 삭제 요청
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "토픽 참여 회원 삭제 요청")
@Alias("frontGroupsDeleteRequest")
public class FrontGroupsDeleteRequest extends BaseRequest {

	@Schema(description = "토픽 참여 시퀀스", example = "1")
	private Long topicMemberSeq;

}
