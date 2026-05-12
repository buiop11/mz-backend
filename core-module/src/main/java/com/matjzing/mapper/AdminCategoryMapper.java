package com.matjzing.mapper;

import com.matjzing.dto.category.*;
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
public interface AdminCategoryMapper {

	List<AdminCategorySelectListResponse> selectAdminCategoryList(AdminCategorySelectListRequest req);
	Page<AdminCategorySelectPageResponse> selectAdminCategoryPage(AdminCategorySelectPageRequest req);
	AdminCategorySelectResponse selectAdminCategory(AdminCategorySelectRequest req);
	Long insertAdminCategory(AdminCategoryInsertRequest req);
	Long updateAdminCategory(AdminCategoryUpdateRequest req);
	Long deleteAdminCategory(AdminCategoryDeleteRequest req);
	
}