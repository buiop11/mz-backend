package com.matjzing.dto.category;

import com.matjzing.dto.common.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import com.matjzing.dto.file.FileUploadDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 등록 요청 모델 작성
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "카테고리 등록 요청 모델")
@Alias("frontCategoryInsertRequest")
public class FrontCategoryInsertRequest extends BaseRequest {

	@Hidden
	@Schema(description = "", example = "")
	private Long categorySeq;

	@NotNull
	@Schema(description = "", example = "")
	private String name;

	@Schema(description = "", example = "")
	private String emoji;

}
