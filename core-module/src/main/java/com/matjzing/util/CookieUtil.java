package com.matjzing.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.Duration;


@Slf4j
@Component
public class CookieUtil {

	@Value("${cookie-domain}")
	private String domain;

	@Value("${spring.profiles.active}")
	private String profile;

	@Value("${jwt.refresh-header}")
	private String HEADER_REFRESH;

	public void setRefreshToken(String value, Long minutes) {
		setSecureCookie(HEADER_REFRESH, value, minutes);
	}

	public void setNormalCookie(String key, String value, Long minutes) {
		setCookie(key, value, minutes, false);
	}
	public void setSecureCookie(String key, String value, Long minutes) {
		setCookie(key, value, minutes, true);
	}

	public String getCookie(String key) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (key.equals(cookie.getName())) {
					log.debug(String.valueOf(cookie.getName()));
					log.debug(String.valueOf(cookie.getValue()));
					log.debug(String.valueOf(cookie.getDomain()));
					log.debug(String.valueOf(cookie.getMaxAge()));
					return cookie.getValue();
				}
			}
		}
		return "";
	}

	private void setCookie(String key, String value, Long minutes, boolean isSecure) {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

		String cookieDomain = domain;
		ResponseCookie cookie = null;
		if ("local".equals(profile)) {
			cookieDomain = "localhost";
		}

		if (isHttps() && isSecure) {
			cookie = ResponseCookie.from(key, value)
					.path("/")
					.secure(true)
					.httpOnly(true)
					.sameSite("None")
					.maxAge(Duration.ofMinutes(minutes))
					.domain(cookieDomain)
					.build();
		} else {
			cookie = ResponseCookie.from(key, value)
					.path("/")
					.maxAge(Duration.ofMinutes(minutes))
					.domain(cookieDomain)
					.build();
		}

		log.info("cookieDomain : {}, isHttps() : {}",cookieDomain, isHttps());

		response.addHeader("Set-Cookie", cookie.toString());
	}

	private boolean isHttps() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String currentProtocol = request.getScheme();
		return currentProtocol.equalsIgnoreCase("https");
	}

}
