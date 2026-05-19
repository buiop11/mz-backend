package com.matjzing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.matjzing.security.JwtService;
import com.matjzing.service.FrontServiceCheckService;
import com.matjzing.util.JwtUtil;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final JwtUtil jwtUtil;
	private final JwtService jwtService;
	private final FrontServiceCheckService serviceCheckService;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new GlobalInterceptor(jwtUtil, jwtService, serviceCheckService))
				.addPathPatterns("/api/**")
				.addPathPatterns("/swagger-ui.html")
				.addPathPatterns("/view/login");
	}

}
