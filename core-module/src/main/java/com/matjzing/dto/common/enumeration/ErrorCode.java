package com.matjzing.dto.common.enumeration;

public enum ErrorCode {
	ERROR("ERR400", ""),
	INVALID_PARAM("ERR400_001", "파라미터 오류"),
	ERR401_001("ERR401_001","토큰만료"),
	ERR401_002("ERR401_002","토큰 위변조"),
	ERR401_003("ERR401_003","토큰 필수값"),
	ERR401_999("ERR401_999","토큰 오류 서버 확인 필요"),

	ERR404_001("ERR404_001","FILE NOT FOUND"),
	ERR404_002("ERR404_002","DATA NOT FOUND"),

	SERVER_ERROR("ERR500_001", "장애가 발생했습니다."),
	SYSTEM_INSPECTION("ERR503_001", "시스템 점검."),

	SYSTEM_UPDATE("ERR503_002", "앱 업데이트."),
	SYSTEM_ERROR("ERR503_003", "앱 버전 체크 에러."),

	;

	private final String code;
	private final String message;

	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}
	public String getMessage() {
		return this.message;
	}

}
