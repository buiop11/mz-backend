package com.matjzing.dto.common.enumeration;

import com.matjzing.util.*;
import org.apache.ibatis.type.MappedTypes;

public enum OsCd implements CodeEnum {

	IOS("iOS"),
	AOS("AOS"),
	OTHER("OTHER"),
	;

	private final String desc;

	public String getDesc() {
		return desc;
	}

	OsCd(String desc) {
		this.desc = desc;
	}

	@MappedTypes(OsCd.class)
	public static class TypeHandler extends CodeEnumTypeHandler<OsCd> {
		public TypeHandler() {
			super(OsCd.class);
		}
	}

}
