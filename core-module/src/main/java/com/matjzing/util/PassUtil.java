package com.matjzing.util;

import com.matjzing.dto.common.PassInfo;
import com.matjzing.dto.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Calendar;

@RequiredArgsConstructor
@Component
public class PassUtil {

	private final Aes256Util aes256Util;

	public String encPassInfo(PassInfo passInfo) {
		conversionTelecomSectionCd(passInfo);
		return aes256Util.encrypt(RestUtil.convertDtoToJsonString(passInfo));
	}

	public void decPassInfo(FrontMemberInsertRequest req) {
		PassInfo passInfo = RestUtil.convertJsonToDto(aes256Util.decrypt(req.getPassInfo()), PassInfo.class);
		req.setName(passInfo.getName());
		req.setBirthday(passInfo.getBirthday());
		req.setGenderCd(passInfo.getGenderCd());
		req.setNativeSectionCd(passInfo.getNativeSectionCd());
		req.setCi(passInfo.getCi());
		req.setDi(passInfo.getDi());
		req.setMobilePhoneNo(passInfo.getMobilePhoneNo());
		req.setTelecomSectionCd(passInfo.getTelecomSectionCd());
	}

	public PassInfo decPassInfo(String passInfo) {
		return RestUtil.convertJsonToDto(aes256Util.decrypt(passInfo), PassInfo.class);
	}


	public String getDi(String encPassInfo) {
		PassInfo passInfo = RestUtil.convertJsonToDto(aes256Util.decrypt(encPassInfo), PassInfo.class);
		return passInfo.getDi();
	}

	public int getAge(String birthday) {
		int birthYear = Integer.parseInt(birthday.substring(0, 4));
		int birthMonth = Integer.parseInt(birthday.substring(4, 6));
		int birthDay = Integer.parseInt(birthday.substring(6, 8));

		Calendar current = Calendar.getInstance();

		int currentYear = current.get(Calendar.YEAR);
		int currentMonth = current.get(Calendar.MONTH) + 1;
		int currentDay = current.get(Calendar.DAY_OF_MONTH);

		int age = currentYear - birthYear;
		if (birthMonth * 100 + birthDay > currentMonth * 100 + currentDay) {
			age--;
		}

		return age;
	}

	private void conversionTelecomSectionCd(PassInfo passInfo) {
		String telecomSectionCd = passInfo.getTelecomSectionCd();
		if (StringUtils.hasText(telecomSectionCd)) {
			switch (telecomSectionCd) {
				case "01":
					passInfo.setTelecomSectionCd("SKT");
					break;
				case "02":
					passInfo.setTelecomSectionCd("KT");
					break;
				case "03":
					passInfo.setTelecomSectionCd("LGU");
					break;
				case "04":
					passInfo.setTelecomSectionCd("SKTM");
					break;
				case "05":
					passInfo.setTelecomSectionCd("KTM");
					break;
				case "06":
					passInfo.setTelecomSectionCd("LGM");
					break;
				default:
					break;
			}
		}
	}

}
