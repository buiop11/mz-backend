package com.matjzing.dto.common.enumeration;

public enum FileTargetCd {
	candidate("candidate"),			// 후보
	profile("profile"),	// 프로필
	excel("excel"),			// 엑셀 다운로드
	;
	private final String desc;

	public String getDesc() {
		return desc;
	}

	FileTargetCd(String desc) {
		this.desc = desc;
	}

}
