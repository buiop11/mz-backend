package com.matjzing.dto.comment;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.ibatis.type.Alias;

/**
 * @author: 김아진
 * @date: 2026-05-14
 * @pname: 관리자
 * @desc: 관리자 페이징 조회 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "댓글 Page 조회 요청 모델")
@Alias("frontCommentSelectPageRequest")
public class FrontCommentSelectPageRequest extends PageRequest {

    @Schema(description = "후보 시퀀스", example = "1")
    @NotNull(message = "필수값입니다.")
    private Long candidateSeq;

}
