package com.matjzing.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.matjzing.dto.common.EmptyResponse;
import com.matjzing.dto.common.ResponseModel;
import com.matjzing.dto.common.enumeration.ErrorCode;
import com.matjzing.dto.common.enumeration.ErrorField;
import com.matjzing.dto.common.enumeration.OsCd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RequiredArgsConstructor
public class RestUtil {

	private static final ObjectMapper objectMapper; // ObjectMapper

	private static final String SUC_CODE = "SUC001";
	private static final String SUC_MSG = "처리가 완료되었습니다.";
	private static final int DEFAULT_BATCH_SIZE = 1000;

	private static final EmptyResponse emptyDto = new EmptyResponse();

	static { // ObjectMapper 생성 및 설정
		objectMapper = new ObjectMapper()
				/*
				 * convertValue 를 사용할 때
				 * fromValue 객체에는 있지만 toValueType 객체에는 없는 필드가 있으면 에러가 발생합니다.
				 * 해당 에러를 발생하지 않기 위해서
				 * fromValue 객체에는 있지만 toValueType 객체에는 없는 필드를 무시하도록 설정
				 */
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(new JavaTimeModule());
	}

	public static <T> ResponseEntity<ResponseModel<EmptyResponse>> ok() {
		return ok(emptyDto, SUC_CODE, SUC_MSG);
	}
	public static <T> org.springframework.http.ResponseEntity ok(final T body) { return ok(body, SUC_CODE, SUC_MSG);}

	private static <T> ResponseEntity<ResponseModel<T>> ok(final T body, final String code, final String message) {
		ResponseModel<T> model = new ResponseModel<T>();

		model.setCode(code);
		model.setMessage(message);
		model.setData(body);

		return ResponseEntity
				.ok()
				.body(model);
	}

	public static <T> ResponseEntity<ResponseModel<T>> notfound() {
		return error(NOT_FOUND, ErrorCode.ERR404_002, null);
	}
	public static <T> ResponseEntity<ResponseModel<T>> falsifyToken() {
		return error(UNAUTHORIZED, ErrorCode.ERR401_002, null);
	}
	public static <T> ResponseEntity<ResponseModel<T>> expiredToken() {
		return error(UNAUTHORIZED, ErrorCode.ERR401_001, null);
	}
	public static <T> ResponseEntity<ResponseModel<T>> error() {
		return error(ErrorCode.ERROR, null);
	}
	public static <T> ResponseEntity<ResponseModel<T>> error(final String errorMsg) {
		return error(ErrorCode.ERROR, errorMsg);
	}
	public static <T> ResponseEntity<ResponseModel<T>> error(final ErrorCode errorCode) {
		return error(errorCode, errorCode.getMessage());
	}
	public static <T> ResponseEntity<ResponseModel<T>> systemMessage(final ErrorCode errorCode, final T body) {
		return error(SERVICE_UNAVAILABLE, errorCode.getCode(), errorCode.getMessage(), errorCode.getMessage(), body );
	}
	public static ResponseEntity<?> error(final ErrorField errorField) {
		return error(BAD_REQUEST, ErrorCode.INVALID_PARAM, errorField.getErrors().toString(), errorField);
	}
	public static <T> ResponseEntity<ResponseModel<T>> error(final String errorCode, final String errorMsg) {
		return error(BAD_REQUEST, errorCode, errorMsg, errorMsg, null );
	}
	public static <T> ResponseEntity<ResponseModel<T>> error(final ErrorCode errorCode, final String errorMsg) {
		return error(BAD_REQUEST, errorCode, errorMsg);
	}
	public static <T> ResponseEntity<ResponseModel<T>> serverError() {
		return error(INTERNAL_SERVER_ERROR, ErrorCode.SERVER_ERROR, null);
	}
	public static <T> ResponseEntity<ResponseModel<T>> error(final HttpStatus status, final ErrorCode errorCode) {
		return error(status, errorCode, errorCode.getMessage(), null );
	}
	public static <T> ResponseEntity<ResponseModel<T>> error(final HttpStatus status, final ErrorCode errorCode, final String errorMsg ) {
		return error(status, errorCode, errorMsg, null );
	}
	public static <T> ResponseEntity<ResponseModel<T>> error(final HttpStatus status, final ErrorCode errorCode, final String errorMsg, final T errorField ) {
		return error(status, errorCode.getCode(), errorCode.getMessage(), errorMsg, errorField );
	}
	private static <T> ResponseEntity<ResponseModel<T>> error(final HttpStatus status, final String code, final String message, final String errorMsg, final T errorField ) {
		ResponseModel<T> model = new ResponseModel<T>();

		model.setCode(code);
		model.setMessage(message);
		model.setData(errorField);

		return ResponseEntity
				.status(status)
				.body(model);
	}

	public static <T> ResponseEntity<ResponseModel<T>> fieldError(final T errorField ) {
		return error(BAD_REQUEST, ErrorCode.INVALID_PARAM.getCode(), ErrorCode.INVALID_PARAM.getMessage(), null, errorField );
	}

	public static <B, A> A convert(final B before, final Class<A> after) {
		return objectMapper.convertValue(before, after);
	}

	public static <B, A> List<A> convert(final List<B> before, final Class<A> after) {
		return before.stream().map(item -> convert(item, after)).collect(Collectors.toList());
	}

	public static <T> T convertJsonToDto(String jsonString, Class<T> valueType) {
		try {
			return objectMapper.readValue(jsonString, valueType);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String convertDtoToJsonString(final Object after) {
		try {
			return objectMapper.writeValueAsString(after);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	public static <B, A> A convertQueryStringToDto(final String queryString, final Class<A> after) {
		Map<String, String> paramMap = UriComponentsBuilder.fromUriString("?" + queryString).build().getQueryParams()
				.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().toLowerCase(), e -> urlDecoding(e.getValue().get(0)), (v1, v2) -> v2, LinkedHashMap::new));
		return convert(paramMap, after);
	}
	public static JSONObject convertDtoToJSONObject(Object dto) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(dto);
			return (JSONObject) JSONValue.parse(json);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String getClientIp () {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getServerIp () {
		String hostAddr = "";
		try {
			Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
			while (nienum.hasMoreElements()) {
				NetworkInterface ni = nienum.nextElement();
				Enumeration<InetAddress> kk= ni.getInetAddresses();
				while (kk.hasMoreElements()) {
					InetAddress inetAddress = kk.nextElement();
					if (!inetAddress.isLoopbackAddress() &&
							!inetAddress.isLinkLocalAddress() &&
							inetAddress.isSiteLocalAddress()) {
						hostAddr = inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return hostAddr;
	}

	public static String getSwaggerPassword(String profile) {
		String password = "";
		if ("dev|local".contains(profile)) {
			password = "d2v";
		} else if ("prod".contains(profile)) {
			password = "pr5d";
		}
		return password;
	}

	public static <E extends Enum<E> & CodeEnum> E getEnumByDesc(String code, Class<E> clazz) {
		return EnumSet.allOf(clazz)
				.stream()
				.filter(value -> value.getDesc().equals(code))
				.findFirst()
				.orElseGet(null);
	}

	public static String exchance(HttpMethod method, String url, HttpHeaders httpHeaders, Object requestBody) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, httpHeaders);

		log.info("=========== http post ===========");
		log.info("url : {}", url);
		log.info("header : {}", httpHeaders);
		log.info("body : {}", RestUtil.convertDtoToJsonString(requestBody));

		ResponseEntity<String> response = restTemplate.exchange(url, method , requestEntity, String.class);

		log.info("response body : {}", response.getBody());
		log.info("=========== http post ===========");

		return response.getBody();
	}

	public static String exchanceMultiValueMap(HttpMethod method, String url, HttpHeaders httpHeaders, MultiValueMap<String, Object> requestBody) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);

		log.info("=========== http post ===========");
		log.info("url : {}", url);
		log.info("header : {}", httpHeaders);
		log.info("body : {}", requestBody);

		ResponseEntity<String> response = restTemplate.exchange(url, method , requestEntity, String.class);

		log.info("response body : {}", response.getBody());
		log.info("=========== http post ===========");

		return response.getBody();
	}

	public static String httpURLConnection(String contentType, HttpMethod method, String requestUrl, Map<String, Object> paramMap) throws IOException {
		String responseBody = "";

		log.info("=========== http post ===========");
		log.info("url : {}", requestUrl);
		log.info("Content-Type : {}", contentType);
		log.info("body : {}", convertDtoToJsonString(paramMap));
		log.info("method : {}", method.name());

		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> params : paramMap.entrySet()) {
			if (postData.length() != 0) {
				postData.append("&");
			}

			postData.append(URLEncoder.encode(params.getKey(), StandardCharsets.UTF_8));
			postData.append("=");
			postData.append(URLEncoder.encode(String.valueOf(params.getValue()), StandardCharsets.UTF_8));
		}

		URL url = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn != null) {
			conn.setRequestProperty("Content-Type", contentType);
			conn.setRequestMethod(method.name());
			conn.setDefaultUseCaches(false);
			conn.setDoOutput(true);

			if (conn.getDoOutput()) {
				conn.getOutputStream().write(postData.toString().getBytes(StandardCharsets.UTF_8));
				conn.getOutputStream().flush();
				conn.getOutputStream().close();
			}

			conn.connect();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			responseBody = br.readLine();
			br.close();
		}

		log.info("response body : {}", responseBody);
		log.info("=========== http post ===========");

		return responseBody;
	}

	public static String comma(final Long num) {
		return NumberFormat.getInstance().format(num);
	}

	public static String getBody(HttpServletRequest request) throws IOException {
		StringBuilder requestBody = new StringBuilder();

		// 요청 본문을 읽기 위한 BufferedReader 생성
		BufferedReader reader = request.getReader();
		String line;

		// BufferedReader를 사용하여 요청 본문을 읽음
		while ((line = reader.readLine()) != null) {
			requestBody.append(line);
		}

		// 읽은 요청 본문을 문자열로 출력
		return requestBody.toString();
	}

	public static String urlDecoding(final String encodeStr) {
		if (StringUtils.hasText(encodeStr)) {
			try {
				return URLDecoder.decode(encodeStr, StandardCharsets.UTF_8);
			} catch (IllegalArgumentException ex) {
				return encodeStr;
			}
		} else {
			return encodeStr;
		}
	}

	public static String getUserAgent() {
		HttpServletRequest request =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		// 운영체제 정보
		String userAgent = request.getHeader("User-Agent");
		if (StringUtils.hasText(userAgent)) {
			userAgent = userAgent.toLowerCase();
		}

		if (userAgent.indexOf("android") > -1) {
			//안드로이드
			return OsCd.AOS.name();
		} else if (userAgent.indexOf("iphone") > -1 || userAgent.indexOf("ipad") > -1 || userAgent.indexOf("ipod") > -1 ) {
			//IOS
			return OsCd.IOS.name();
		} else {
			//기타 WEB
			return OsCd.OTHER.name();
		}
	}

	/**
	 * 배치 insert 혹은 update <br>
	 * 기본 배치 사이즈(5000)으로 설정
	 *
	 * <pre>
	 * public class Service {
	 *
	 *  private final Mapper mapper;
	 *
	 *  public void batch () {
	 *    List&lt;BatchInsertRequest&gt; batchList = new ArrayList&lt;&gt;();
	 *    batchList.add(ActionLogInsertRequest.builder()
	 *        .osSectionCd(RestUtil.getUserAgent())
	 *        .requestMethodCd(request.getMethod())
	 *        .requestUrl(String.valueOf(request.getRequestURL()))
	 *        .executionTime(1000L)
	 *        .build());
	 *    processInBatches(batchList, mapper::insertManagerActionLogByForeach);
	 *  }
	 * }
	 *
	 * public interface ActionLogMapper {
	 *   Long insertManagerActionLogByForeach(List<ActionLogInsertRequest> list);
	 * }
	 *
	 * &lt;insert id="insertManagerActionLog" parameterType="java.util.List"&gt;
	 * 	INSERT INTO MANAGER_ACTION_LOG (
	 * 		  // .....
	 * 	) VALUES
	 * 	&lt;foreach collection="list" item="item" separator=","&gt;
	 * 	(
	 * 		 // .....
	 * 	)
	 * 	&lt;/foreach&gt;
	 * &lt;/insert&gt;
	 *
	 * </pre>
	 *
	 * @param list 배치 대상 목록
	 * @param func 배치 대상 메소드
	 * @param <T> 배치 대상 클래스 타입
	 */
	public static <T> void processInBatches(List<T> list, Consumer<List<T>> func) {
		processInBatches(list, func, DEFAULT_BATCH_SIZE);
	}

	/**
	 * 배치 insert 혹은 update <br>
	 * 배치 사이즈를 원하는 사이즈로 설정 가능
	 *
	 * <pre>
	 * public class Service {
	 *
	 *  private final Mapper mapper;
	 *  private final int BATCH_SIZE = 10000;
	 *
	 *  public void batch () {
	 *    List&lt;BatchInsertRequest&gt; batchList = new ArrayList&lt;&gt;();
	 *    batchList.add(ActionLogInsertRequest.builder()
	 *        .osSectionCd(RestUtil.getUserAgent())
	 *        .requestMethodCd(request.getMethod())
	 *        .requestUrl(String.valueOf(request.getRequestURL()))
	 *        .executionTime(1000L)
	 *        .build());
	 *    processInBatches(batchList, mapper::insertManagerActionLogByForeach, BATCH_SIZE);
	 *  }
	 * }
	 *
	 * public interface ActionLogMapper {
	 *   Long insertManagerActionLogByForeach(List<ActionLogInsertRequest> list);
	 * }
	 *
	 * &lt;insert id="insertManagerActionLog" parameterType="java.util.List"&gt;
	 * 	INSERT INTO MANAGER_ACTION_LOG (
	 * 		  // .....
	 * 	) VALUES
	 * 	&lt;foreach collection="list" item="item" separator=","&gt;
	 * 	(
	 * 		 // .....
	 * 	)
	 * 	&lt;/foreach&gt;
	 * &lt;/insert&gt;
	 *
	 * </pre>
	 *
	 * @param list 배치 대상 목록
	 * @param func 배치 대상 메소드
	 * @param batchSize 배치 사이즈
	 * @param <T> 배치 대상 클래스 타입
	 */
	public static  <T> void processInBatches(List<T> list, Consumer<List<T>> func, int batchSize) {
		int i = 0;
		while (i < list.size()) {
			List<T> batch = list.subList(i, Math.min(list.size(), i + batchSize));
			func.accept(batch);
			i += batchSize;
		}
	}

}
