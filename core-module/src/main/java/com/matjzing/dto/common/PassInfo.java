package com.matjzing.dto.common;

import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassInfo {

	private String name;
	private String birthday;
	private String genderCd;
	private String nativeSectionCd;
	private String ci;
	private String di;
	private String mobilePhoneNo;
	private String telecomSectionCd;

}
