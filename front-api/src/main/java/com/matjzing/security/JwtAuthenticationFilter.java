package com.matjzing.security;

import com.matjzing.dto.common.enumeration.ErrorCode;
import com.matjzing.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String token = jwtService.resolveToken(request);

		try {
			if (token != null) {
				if (jwtService.isTokenValid(request, token)) {
					Authentication auth = jwtService.getAuthentication(token);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			}
		} catch (UserNotFoundException ex) {
			request.setAttribute("ERROR_CODE", ErrorCode.ERR401_002);
		}

		filterChain.doFilter(request, response);
	}

}
