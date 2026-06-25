package com.matjzing.dto.candidate;

import com.matjzing.dto.common.BaseRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = false)
@Data
@Schema(description = "링크에서 대표 이미지 추출 요청")
@Alias("frontCandidateScrapeImageRequest")
public class FrontCandidateScrapeImageRequest extends BaseRequest {

	@Schema(description = "상품 페이지 URL", example = "https://www.musinsa.com/products/5887859")
	@NotBlank(message = "url은 필수입니다.")
	private String url;
}
