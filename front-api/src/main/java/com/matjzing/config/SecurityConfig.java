package com.matjzing.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.matjzing.security.JwtAuthenticationEntryPoint;
import com.matjzing.security.JwtAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private final CorsConfig corsConfig;
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	public final static String[] URL_WHITE_LIST = {
			  "/api/swagger/**"				// Swagger API
			, "/login"					    // Swagger 로그인 API
			, "/view/login"					// Swagger 로그인 페이지
			, "/api/login"					// 로그인
			, "/api/login/google"			// Google OIDC 로그인
			, "/api/access-token"			// 엑세스토큰 갱신
			, "/api/member"                 // 회원 가입
			, "/api/member/check/id"        // 회원 가입시 아이디 중복체크
			, "/api/access-token"    		// access 토큰 갱신
			, "/swagger-ui/**"
			, "/v3/api-docs/**" // ✅ OpenAPI 문서 접근 허용
			, "/swagger-ui.html"
			, "/v3/api-docs/swagger-config"

			/*
			,"/api/vote"		// front 임시 이하 아래
			,"/api/vote/**"
			,"/api/topic"
			,"/api/topic/**"
			,"/api/groups"
			,"/api/groups/**"
			,"/api/excalendar"
			,"/api/excalendar/**"
			,"/api/category"
			,"/api/category/**"
			,"/api/candidate"
			,"/api/candidate/**"
			,"/api/comment"
			,"/api/comment/**"
			*/
	};

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(URL_WHITE_LIST).permitAll()
						.anyRequest().authenticated()
				)
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.exceptionHandling(ex -> ex
						.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
				.build();
	}
}
