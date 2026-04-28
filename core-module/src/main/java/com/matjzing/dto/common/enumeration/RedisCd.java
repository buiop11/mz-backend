package com.matjzing.dto.common.enumeration;

public enum RedisCd {

	COM002("시스템점검정보", "COM002"),
	;

	private final String desc;
	private final String detail;

	public String getDesc(){
		return desc;
	}
	public String getDetail(){
		return detail;
	}

	RedisCd(String desc, String detail){
		this.desc = desc;
		this.detail = detail;
	}

}
