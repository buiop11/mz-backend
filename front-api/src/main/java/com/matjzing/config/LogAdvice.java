package com.matjzing.config;

import com.matjzing.dto.actionlog.ActionLogInsertRequest;
import com.matjzing.service.ActionLogService;
import com.matjzing.util.FilterUtil;
import com.matjzing.util.RequestUtil;
import com.matjzing.util.RestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LogAdvice extends FilterUtil {

	private final ActionLogService service;

	@Pointcut("within(com.matjzing.controller..*)")
	public void onRequest() {
	}

	@Around("com.matjzing.config.LogAdvice.onRequest()")
	public Object requestLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		long start = System.currentTimeMillis();
		try {
			return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
		} finally {
			if (request.getRequestURI().startsWith("/api") && !isFilterWhiteList(request.getServletPath())) {
				long end = System.currentTimeMillis();
				Long executionTime = end - start;

				logRequest(request, executionTime);
				saveActionLog(request, executionTime);
			}
		}
	}

	private void logRequest(HttpServletRequest request, long executionTime) {
		String param = RequestUtil.removeMapInKey(request);
		String body = RequestUtil.removeJsonObjectInKey(request);
		log.debug("Request: {} {}: {} {} ({}ms)", request.getMethod(), request.getRequestURL(), param, body, executionTime);
	}

	private void saveActionLog(HttpServletRequest request, long executionTime) {
		service.insertMemberActionLog(ActionLogInsertRequest.builder()
				.osSectionCd(RestUtil.getUserAgent())
				.requestMethodCd(request.getMethod())
				.requestUrl(String.valueOf(request.getRequestURL()))
				.executionTime(executionTime)
				.build());
	}

}


