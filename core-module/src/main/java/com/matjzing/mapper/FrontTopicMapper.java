package com.matjzing.mapper;

import com.matjzing.dto.topic.*;
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
public interface FrontTopicMapper {

	List<FrontTopicSelectListResponse> selectFrontTopicList(FrontTopicSelectListRequest req);
	Page<FrontTopicSelectPageResponse> selectFrontTopicPage(FrontTopicSelectPageRequest req);
	FrontTopicSelectResponse selectFrontTopic(FrontTopicSelectRequest req);
	Long insertFrontTopic(FrontTopicInsertRequest req);

	int insertTopicMember(FrontTopicInsertRequest req);

	int countActiveTopic(Long topicSeq);

	int insertTopicParticipant(FrontTopicMemberJoinRequest req);

	Long updateFrontTopic(FrontTopicUpdateRequest req);
	Long deleteFrontTopic(FrontTopicDeleteRequest req);
	
}