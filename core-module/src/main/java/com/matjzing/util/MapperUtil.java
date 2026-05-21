package com.matjzing.util;

import com.matjzing.dto.common.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@Alias("MapperUtil")
public class MapperUtil {

	public static void setBaseRequest(final BaseRequest dto) {
		LoginUtil loginUtil = new LoginUtil();
		Long fromJwt = loginUtil.getLoginUserSeq();
		Long loginUserSeq;
		if (fromJwt != null && fromJwt != 0L) {
			loginUserSeq = fromJwt;
		} else if (dto.getMemberSeq() != null) {
			loginUserSeq = dto.getMemberSeq();
		} else {
			loginUserSeq = 0L;
		}
		LocalDateTime now = LocalDateTime.now();
		String ip = RestUtil.getClientIp();

		dto.setMemberSeq(loginUserSeq);
		dto.setRegisterSeq(loginUserSeq);
		dto.setRegistrationDt(now);
		dto.setUpdaterSeq(loginUserSeq);
		dto.setUpdateDt(now);
		dto.setRegisterIp(ip);
		dto.setUpdaterIp(ip);
	}

	/**
	 * 조회 API용: JWT의 회원 seq를 memberSeq로 설정합니다(이미 값이 있으면 유지).
	 */
	public static void bindMemberSeqFromLogin(BaseRequest dto) {
		if (dto.getMemberSeq() != null && dto.getMemberSeq() != 0L) {
			return;
		}
		LoginUtil loginUtil = new LoginUtil();
		Long fromJwt = loginUtil.getLoginUserSeq();
		if (fromJwt != null && fromJwt != 0L) {
			dto.setMemberSeq(fromJwt);
		}
	}

	public static void setBatchBaseRequest(final BaseRequest dto) {
		LocalDateTime now = LocalDateTime.now();
		String ip = RestUtil.getServerIp();

		dto.setRegisterSeq(0L);
		dto.setRegistrationDt(now);
		dto.setUpdaterSeq(0L);
		dto.setUpdateDt(now);
		dto.setRegisterIp(ip);
		dto.setUpdaterIp(ip);
	}


	public static void setDateRequest(final DateRequest dto) {
		if (null != dto.getBeginDt() && null != dto.getEndDt()) {
			dto.setQueryBeginDt(parseLocalDateToLocalDateTime(dto.getBeginDt(), LocalTime.MIN));
			dto.setQueryEndDt(parseLocalDateToLocalDateTime(dto.getEndDt(), LocalTime.MAX));
		}
	}

	public static void setBeginDateRequest(final DateRequest dto) {
		if (null != dto.getBeginDt()) {
			dto.setQueryBeginDt(parseLocalDateToLocalDateTime(dto.getBeginDt(), LocalTime.MIN));
		}
	}

	public static void setEndDateRequest(final DateRequest dto) {
		if (null != dto.getEndDt()) {
			dto.setQueryEndDt(parseLocalDateToLocalDateTime(dto.getEndDt(), LocalTime.MAX));
		}
	}

	public static LocalDateTime parseLocalDateToLocalDateTime(final LocalDate localDate, final LocalTime time) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(SwaggerSample.LOCAL_DATE_TIME_FORMAT);
		return LocalDateTime.parse(of(localDate, time).format(format), format);
	}

	public static String localDateTimeToFormatString(final LocalDateTime localDateTime) {
		if (null != localDateTime) {
			String dateString = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			return dateString.replace(" ", "T");
		} else {
			return null;
		}
	}

	public static String localDateToFormatString(final LocalDate localDate) {
		if (null != localDate) {
			return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} else {
			return null;
		}
	}

	private static LocalDateTime of(final LocalDate localDate, final LocalTime time) {
		return LocalDateTime.of(localDate, localDate.atTime(time).toLocalTime());
	}

	public static boolean isEmpty(Object obj) {
		return ObjectUtils.isEmpty(obj);
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	public static <T> EPageInfo<T> listToEPageInfo(List<T> list, int pageNum, int pageSize) {
		EPageInfo<T> result = new EPageInfo<T>();

		int totalCount = list.size();
		int pages = pageSize > 0 ? totalCount / pageSize + ((totalCount % pageSize == 0) ? 0 : 1) : 0;

		int startIndex = (pageNum - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, list.size());

		List<T> pageItems = list.subList(startIndex, endIndex);

		result.setList(pageItems);
		result.setTotalCount(totalCount);
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setPages(pages);
		result.setNextPage(pageNum < pages ? pageNum + 1 : 0);
		result.setIsFirstPage(pageNum == 1);
		result.setIsLastPage(pageNum == pages || pages == 0);
		result.setHasNextPage(pageNum < pages);

		return result;
	}

}
