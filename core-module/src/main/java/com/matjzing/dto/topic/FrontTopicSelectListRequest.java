package com.matjzing.dto.topic;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 목록 조회 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "안건 List 조회 요청 모델")
@Alias("frontTopicSelectListRequest")
public class FrontTopicSelectListRequest extends PageRequest {

    @Schema(description = "카테고리 시퀀스", example = "1")
    private Long categorySeq;

    @Schema(description = "true: PICK 상태만, false: VOTING 상태만, 미입력 시 전체", example = "false")
    private Boolean picked;
}
