package com.matjzing.mapper;

import com.matjzing.dto.groups.*;
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
public interface FrontGroupsMapper {

	List<FrontGroupsSelectListResponse> selectFrontGroupsList(FrontGroupsSelectListRequest req);
	Page<FrontGroupsSelectPageResponse> selectFrontGroupsPage(FrontGroupsSelectPageRequest req);
	FrontGroupsSelectResponse selectFrontGroups(FrontGroupsSelectRequest req);
	Long insertFrontGroups(FrontGroupsInsertRequest req);
	Long updateFrontGroups(FrontGroupsUpdateRequest req);
	Long deleteFrontGroups(FrontGroupsDeleteRequest req);
	
}