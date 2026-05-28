package com.matjzing.dto.candidate;

import com.matjzing.dto.common.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "후보 PICK 처리 요청 (TOPIC.STATUS='PICK', TOPIC.CANDIDATE_SEQ 업데이트)")
@Alias("frontCandidatePickRequest")
public class FrontCandidatePickRequest extends BaseRequest {

	@Schema(hidden = true)
	private Long candidateSeq;
}

