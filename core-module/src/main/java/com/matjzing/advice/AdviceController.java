package com.matjzing.advice;

import io.jsonwebtoken.ExpiredJwtException;
import com.matjzing.dto.common.enumeration.*;
import com.matjzing.exception.*;
import com.matjzing.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class AdviceController {

	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<?> methodArgumentNotValidException(final MethodArgumentNotValidException ex) {
		printStackTraceInfo(ex);
		return RestUtil.error(ErrorField.of(ex.getBindingResult()));
	}

	@ExceptionHandler(value = {BindException.class})
	public ResponseEntity<?> bindException(final BindingResult bindingResult) {
		return RestUtil.error(ErrorField.of(bindingResult));
	}

	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<?> serverException(final Exception ex) {
		printStackTraceInfo(ex);
		return RestUtil.serverError();
	}

	@ExceptionHandler(value = {MessageException.class})
	public ResponseEntity<?> messageException(final MessageException ex) {
		printStackTraceInfo(ex);
		return RestUtil.systemMessage(ErrorCode.SYSTEM_INSPECTION, ex.getMessageResponse());
	}

	@ExceptionHandler(value = {CustomException.class})
	public ResponseEntity<?> customException(final CustomException ex) {
		printStackTraceInfo(ex);
		return RestUtil.error(ex.getCode(), ex.getMessage());
	}

	@ExceptionHandler(value = {NotfoundException.class})
	public ResponseEntity<?> notfoundException(final NotfoundException ex) {
		printStackTraceInfo(ex);
		return RestUtil.notfound();
	}

	@ExceptionHandler(value = {JwtException.class})
	public ResponseEntity<?> jwtException(final JwtException ex) {
		printStackTraceInfo(ex);
		return RestUtil.falsifyToken();
	}

	@ExceptionHandler(value = {CustomFieldException.class})
	public ResponseEntity<?> customFieldException(final CustomFieldException ex) {
		printStackTraceInfo(ex);
		return RestUtil.fieldError(ex.getErrorField());
	}


	@ExceptionHandler(value = {ExpiredJwtException.class})
	public ResponseEntity<?> expiredJwtException(final ExpiredJwtException ex) {
		printStackTraceInfo(ex);
		return RestUtil.expiredToken();
	}

	private void printStackTraceInfo(final Exception error) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		String START_LOGGING = "\n====================================================================\n";
		String END_LOGGING = "====================================================================\n";
		StringBuilder sb = new StringBuilder();

		sb.append(START_LOGGING);
		sb.append("[ERROR] Exception \t: " + error.getClass().getSimpleName());
		sb.append("[ERROR] Request URI \t: " + request.getRequestURI());
		sb.append("[ERROR] Request Method \t: " + request.getMethod());
		sb.append("[ERROR] Param \t: " + RequestUtil.removeMapInKey(request));
		String body = RequestUtil.removeJsonObjectInKey(request);
		if (StringUtils.hasText(body)) {
			sb.append("[ERROR] Body \t: " + body);
		}
		sb.append("[ERROR] Cause \t\t: " + error.getCause());
		sb.append("[ERROR] Message \t\t: " + error.getMessage());

		if (error instanceof CustomException && !ObjectUtils.isEmpty(((CustomException) error).getErrorDetail())) {
			ErrorDetail errorDetail = ((CustomException) error).getErrorDetail();
			sb.append("[ERROR] Detail Code \t: " + errorDetail.getCode());
			sb.append("[ERROR] Detail Message \t: " + errorDetail.getMessage());
		}

		for (StackTraceElement element : error.getStackTrace()) {
			String target = element.toString();
			if (target.contains("com.matjzing")) {
				sb.append("[ERROR] at " + element);
			}
		}
		sb.append(END_LOGGING);

		// 실제 에러 로그를 남기지 않아 500 원인 추적이 불가능했던 문제 보정
		log.error(sb.toString(), error);
	}

}