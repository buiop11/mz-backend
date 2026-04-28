package com.matjzing.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@SecurityScheme(
		name = "Authorization",
		type = SecuritySchemeType.APIKEY,
		scheme = "bearer",
		bearerFormat = "JWT",
		in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

	@Value("${jwt.header}")
	private String HEADER_AUTH;

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("matjzing admin")
						.description(description)
						.version("1.0"))
				.addSecurityItem(new SecurityRequirement().addList("Authorization"));
	}

	//	private SecurityContext securityContext() {
//		return SecurityContext
//				.builder()
//				.securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
//	}
//
//
//	List<SecurityReference> defaultAuth() {
//		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//		authorizationScopes[0] = authorizationScope;
//		return List.of(new SecurityReference("JWT", authorizationScopes));
//	}

	private final String UPDATE_DATE = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	private final String UPDATE_VIEW = "<span style=\"color: red;font-size:40px;\">서버 업데이트 일자</span><br><li style=\"color: red;font-size:30px;\">"+UPDATE_DATE+"</li><br><br>\n\n";
	private final String description = UPDATE_VIEW +
			"\n" +
			"## HTTP STATUS CODE\n" +
			"<details> <summary>내용보기</summary>\n\n"+
			"|status|description|\n" +
			"|:---:|:---|\n" +
			"|200|`'성공'`                    |\n" +
			"|400|`'요청 파라미터 에러 (코드 확인)'`   |\n" +
			"|401|`'인증 에러'`                 |\n" +
			"|500|`'서버 에러 -> 백엔드 문의'`   |\n" +

			"\n\n" +
			"</details>\n\n"+

			"## COMMON CODE\n" +
			"<details> <summary>내용보기</summary>\n\n"+
			"|status|code|description|\n" +
			"|:---:|:---|:---|\n" +
			"|200|SUC200_001|처리 완료|\n" +
			"|-|-|-|\n" +
			"|400|ERR400_001|파라미터 오류|\n" +

			"|401|ERR401_001|토큰만료|\n" +
			"|401|ERR401_002|토큰 위변조|\n" +
			"|401|ERR401_003|토큰 필수 값|\n" +
			"|401|ERR401_999|토큰 오류 서버 확인 필요|\n" +

			"|404|ERR404_001|FILE NOT FOUND|\n" +
			"|404|ERR404_002|DATA NOT FOUND|\n" +

			"|500|ERR500_001|Server Unchecked Exception (서버 에러) -> 백엔드 문의|\n" +

			"|503|ERR503_001|시스템점검(내용 포함)|\n" +
			"|503|ERR503_002|강제 앱 업데이트|\n" +
			"|503|ERR503_003|앱 버전 체크 에러|\n" +

			"</details>\n\n"+

			"## 개별 API ERROR CODE(API 별 오류, 아래 예, ERR_[테이블명]_001 )\n" +
			"<details> <summary>내용보기</summary>\n\n"+
			"|status|code|description|\n" +
			"|:---:|:---|:---|\n" +
			"|400|ERR_BBS_001|제목을 입력해주세요.|\n" +
			"|400|ERR_MEMBER_001|아이디는 필수 입력값입니다|\n" +
			"|400|ERR_MENU_001|메뉴명을 입력해주세요.|\n" +

			"</details>\n\n"
			;

}
