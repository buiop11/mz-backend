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
public interface FrontCategoryMapper {

	List<FrontCategorySelectListResponse> selectFrontCategoryList(FrontCategorySelectListRequest req);
	Page<FrontCategorySelectPageResponse> selectFrontCategoryPage(FrontCategorySelectPageRequest req);
	FrontCategorySelectResponse selectFrontCategory(FrontCategorySelectRequest req);
	Long insertFrontCategory(FrontCategoryInsertRequest req);
	Long updateFrontCategory(FrontCategoryUpdateRequest req);
	Long deleteFrontCategory(FrontCategoryDeleteRequest req);
	
}