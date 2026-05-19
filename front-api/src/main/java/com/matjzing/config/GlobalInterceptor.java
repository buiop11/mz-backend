package com.matjzing.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import com.matjzing.dto.common.JwtAuthenticationVo;
import com.matjzing.dto.common.enumeration.JwtType;
import com.matjzing.security.JwtService;
import com.matjzing.service.FrontServiceCheckService;
import com.matjzing.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

	private final JwtUtil jwtUtil;
	private final JwtService jwtService;
	private final FrontServiceCheckService serviceCheckService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// Swagger 로그인 체크
		if (!jwtUtil.isCheckSwaggerToken(request.getRequestURI(), response)) {
			return false;
		}

		if (!jwtUtil.isCheckToken(request.getRequestURI(), SecurityConfig.URL_WHITE_LIST)) {
			//토큰 사용자 처리 (Authorization: Bearer {token} 접두사 제거)
			String token = jwtService.resolveToken(request);
			if (StringUtils.hasText(token)) {
				JwtAuthenticationVo jwtAuthenticationVo = jwtUtil.getTokenVo(token);
				if (JwtType.access.equals(jwtAuthenticationVo.getJwtType())) {
					log.info("jwt info : {}", jwtAuthenticationVo);
					request.setAttribute("jwtAuthenticationVo", jwtAuthenticationVo);
				}
			}

			// Swagger 접속 유틸 기능 체크 안함
			if (!StringUtils.hasText(request.getHeader("Referer"))
					|| (StringUtils.hasText(request.getHeader("Referer")) && !request.getHeader("Referer").contains("swagger-ui.html"))) {
				// 시스템 점검
				serviceCheckService.isServiceCheck();
			}
		}

		return true;
	}

}
