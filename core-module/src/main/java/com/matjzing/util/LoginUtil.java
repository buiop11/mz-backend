package com.matjzing.util;

import com.matjzing.dto.common.JwtAuthenticationVo;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class LoginUtil {

	public Long getLoginUserSeq() {

		if (ObjectUtils.isEmpty(RequestContextHolder.getRequestAttributes())) {
			return 0L;
		} else {
			HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			JwtAuthenticationVo jwtAuthenticationVo = (JwtAuthenticationVo) req.getAttribute("jwtAuthenticationVo");
			if (!ObjectUtils.isEmpty(jwtAuthenticationVo)) {
				return jwtAuthenticationVo.getSeq();
			} else {
				return null;
			}
		}
	}

}
