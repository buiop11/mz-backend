package com.matjzing.mapper;

import com.matjzing.dto.candidate.*;
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
public interface FrontCandidateMapper {

	List<FrontCandidateSelectListResponse> selectFrontCandidateList(FrontCandidateSelectListRequest req);
	Page<FrontCandidateSelectPageResponse> selectFrontCandidatePage(FrontCandidateSelectPageRequest req);
	FrontCandidateSelectResponse selectFrontCandidate(FrontCandidateSelectRequest req);
	Long insertFrontCandidate(FrontCandidateInsertRequest req);
	Long updateFrontCandidate(FrontCandidateUpdateRequest req);
	Long deleteFrontCandidate(FrontCandidateDeleteRequest req);
	
}