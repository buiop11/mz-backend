package com.matjzing.dto.common.enumeration;

public enum FileTargetCd {
	bbs("bbs"),			// 공지사항
	profile("profile"),	// 프로필
	display_banner("display/banner"),	// 전시_배너
	webeditor_temp("webeditor/temp"), // 웹 에디터 임시 파일업로드 버킷
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
