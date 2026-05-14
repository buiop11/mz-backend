package com.matjzing.mapper;

import com.matjzing.dto.comment.*;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-05-14
 * @pname: 관리자
 * @desc: 관리자 매퍼 인터페이스 작성
 */
@Repository
public interface FrontCommentMapper {

	List<FrontCommentSelectListResponse> selectFrontCommentList(FrontCommentSelectListRequest req);
	Page<FrontCommentSelectPageResponse> selectFrontCommentPage(FrontCommentSelectPageRequest req);
	Long insertFrontComment(FrontCommentInsertRequest req);
	Long updateFrontComment(FrontCommentUpdateRequest req);
	Long deleteFrontComment(FrontCommentDeleteRequest req);
	
}