package com.matjzing.dto.topic;

import com.matjzing.dto.common.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * PICK 완료 로그 목록 조회 요청
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "PICK 완료 로그 목록 조회 요청")
@Alias("frontTopicPickListRequest")
public class FrontTopicPickListRequest extends BaseRequest {

}
