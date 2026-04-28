package com.matjzing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.matjzing.service.FrontServiceCheckService;
import com.matjzing.util.JwtUtil;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private final JwtUtil jwtUtil;

	private final FrontServiceCheckService serviceCheckService;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new GlobalInterceptor(jwtUtil, serviceCheckService)).addPathPatterns("/api/**").addPathPatterns("/swagger-ui.html").addPathPatterns("/view/login");
	}

}
