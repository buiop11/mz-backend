package com.matjzing.mapper;

import com.matjzing.dto.excalendar.*;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 매퍼 인터페이스 작성
 */
@Repository
public interface FrontExcalendarMapper {

	List<FrontExcalendarSelectListResponse> selectFrontExcalendarList(FrontExcalendarSelectListRequest req);
	Page<FrontExcalendarSelectPageResponse> selectFrontExcalendarPage(FrontExcalendarSelectPageRequest req);
	FrontExcalendarSelectResponse selectFrontExcalendar(FrontExcalendarSelectRequest req);
	Long insertFrontExcalendar(FrontExcalendarInsertRequest req);
	Long updateFrontExcalendar(FrontExcalendarUpdateRequest req);
	Long deleteFrontExcalendar(FrontExcalendarDeleteRequest req);
	
}