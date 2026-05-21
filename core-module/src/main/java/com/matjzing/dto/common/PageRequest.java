package com.matjzing.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageRequest extends BaseRequest {

	@Schema(description = "페이지 번호", example = "1")
	private int currentPage;

}
