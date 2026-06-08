package com.matjzing.dto.topic;

import com.matjzing.dto.common.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * PICK(후보 결정) 번복 요청
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "PICK 번복 요청 (STATUS→VOTING, CANDIDATE_SEQ→NULL, 후보 IS_FIXED→FALSE)")
@Alias("frontTopicPickRevertRequest")
public class FrontTopicPickRevertRequest extends BaseRequest {

	@Schema(hidden = true)
	private Long topicSeq;

}
