package com.matjzing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 로컬에서 후보군 이미지 보여주기 연결, nginx가 해야할 일을 springboot가 하게 만든 것
		registry.addResourceHandler("/candidate/**")
				.addResourceLocations("file:///C:/app/file/candidate/");
	}

}
