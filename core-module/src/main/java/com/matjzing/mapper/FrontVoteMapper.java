package com.matjzing.mapper;

import com.matjzing.dto.vote.*;
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
public interface FrontVoteMapper {

	List<FrontVoteSelectListResponse> selectFrontVoteList(FrontVoteSelectListRequest req);
	Page<FrontVoteSelectPageResponse> selectFrontVotePage(FrontVoteSelectPageRequest req);
	FrontVoteSelectResponse selectFrontVote(FrontVoteSelectRequest req);
	Long insertFrontVote(FrontVoteInsertRequest req);
	Long updateFrontVote(FrontVoteUpdateRequest req);
	Long deleteFrontVote(FrontVoteDeleteRequest req);
	
}