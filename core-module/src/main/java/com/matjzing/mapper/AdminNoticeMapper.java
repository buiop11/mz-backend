package com.matjzing.mapper;

import com.matjzing.dto.notice.*;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-04-23
 * @pname: 관리자
 * @desc: 관리자 매퍼 인터페이스 작성
 */
@Repository
public interface AdminNoticeMapper {

	List<AdminNoticeSelectListResponse> selectAdminNoticeList(AdminNoticeSelectListRequest req);
	Page<AdminNoticeSelectPageResponse> selectAdminNoticePage(AdminNoticeSelectPageRequest req);
	AdminNoticeSelectResponse selectAdminNotice(AdminNoticeSelectRequest req);
	Long insertAdminNotice(AdminNoticeInsertRequest req);
	Long updateAdminNotice(AdminNoticeUpdateRequest req);
	Long deleteAdminNotice(AdminNoticeDeleteRequest req);
	
}