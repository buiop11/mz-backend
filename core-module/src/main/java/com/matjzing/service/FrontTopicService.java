package com.matjzing.service;

import com.matjzing.dto.common.*;
import com.matjzing.dto.topic.*;
import com.matjzing.exception.NotfoundException;
import com.matjzing.mapper.FrontTopicMapper;
import com.matjzing.util.*;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 서비스 작성
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FrontTopicService {

//	private enum FrontTopicERRCd {
//		ERR_TOPIC_001("안건 List 조회 실패"),
//		;
//		private final String desc;
//		public String getDesc() {
//			return desc;
//		}
//		FrontTopicERRCd(String desc) {
//			this.desc = desc;
//		}
//	}

	private final FrontTopicMapper mapper;
	private final CommonFileService commonFileService;

	public List<FrontTopicSelectListResponse> list(FrontTopicSelectListRequest req) {
		MapperUtil.bindMemberSeqFromLogin(req);
		return mapper.selectFrontTopicList(req);
	}

	public List<FrontTopicPickListResponse> pickList(FrontTopicPickListRequest req) {
		MapperUtil.bindMemberSeqFromLogin(req);
		return mapper.selectFrontTopicPickList(req);
	}

	public EPageInfo<FrontTopicSelectPageResponse> page(FrontTopicSelectPageRequest req) {
		MapperUtil.bindMemberSeqFromLogin(req);
		PageHelper.startPage(req.getCurrentPage(), 10);
		/*
		 	// 기간검색 1개 일 경우

		 	ex)
		 	1. 요청 객체 DateRequest 상속
		 	public class BbsSelectPageRequest {
		 		...
		 	}

		 	->

		 	public class BbsSelectPageRequest extends DateRequest {
		 		...
		 	}

		 	2. 검색 시작일 + "00:00:00", 검색 종료일 + "23:59:59" 셋팅
		 	MapperUtil.setDateRequest(req);

		 	3. Mapper.xml 쿼리 queryBeginDt, queryEndDt 사용
		 	<select>
		 		SELECT
		 			....
		 		FROM
		 			....
		 		WHERE
		 			REGISTRATION_DT >= #{queryBeginDt}
		 			AND REGISTRATION_DT <= #{queryEndDt}
		 	</select>


		 	// 파일이 있는 경우
			EPageInfo<FrontTopicSelectPageResponse> res = new EPageInfo<>(mapper.selectFrontTopicPage(req));

			for (FrontTopicSelectPageResponse frontTopicSelectPageResponse : res.getList()) {
				frontTopicSelectPageResponse.setFileList(commonFileService.getFileList(true, FileTargetCd.bbs, req.getFrontTopicSeq()));
			}
			return res;

			// return type이 Page + Object 일 경우
			public class BbsSelectPageResponse {
				@Schema(description = "배너 목록")
				private List<FrontCampaignSelectBannerListResponse> bannerList;

				@Schema(description = "캠페인 목록")
				private EPageInfo<FrontCampaignSelectPageListResponse> campaignList;
		 	}

			// 조회 기능 분리
		 	public class Service {

		 		public BbsSelectPageResponse page(BbsSelectPageRequest req) {
					BbsSelectPageResponse res = new BbsSelectPageResponse();
					res.setBannerList(selectBanner(req));
					res.setCampaignList(selectCampaignPage(req));
		 		}

		 		private List<FrontCampaignSelectBannerListResponse> selectBanner(BbsSelectPageRequest req) {
		 			....
				}

		 		private EPageInfo<FrontCampaignSelectPageListResponse> selectCampaignPage(BbsSelectPageRequest req) {
		 			....
				}
		 	}

		 */
		return new EPageInfo<>(mapper.selectFrontTopicPage(req));
	}

	public FrontTopicSelectResponse detail(FrontTopicSelectRequest req) {

		MapperUtil.bindMemberSeqFromLogin(req);
		FrontTopicSelectResponse response = mapper.selectFrontTopic(req);

		// 게시판 상세페이지 일 경우 no-data 케이스 용
		if (null == response) {
			throw new NotfoundException();
		}

		/*
			// 파일이 있는 경우
			if (null == response) {
				// 게시판 상세페이지 일 경우 no-data 케이스 용
				throw new NotfoundException();
			} else {
				response.setFileList(commonFileService.getFileList(true, FileTargetCd.bbs, req.getFrontTopicSeq()));
			}
		 */
		return response;
	}

	@Transactional
	public void insert(FrontTopicInsertRequest req) {

		/*
			// 서비스 단 에러처리
			if (dto.getTitle().length() == 1) {
				throw new CustomException(FrontTopicERRCd.ERR_TOPIC_001.toString(), FrontTopicERRCd.ERR_TOPIC_001.getDesc());
			}
		 */
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
		mapper.insertFrontTopic(req); // 등록처리
		mapper.insertTopicMember(req); // TOPIC_MEMBER OWNER (등록자)

		/*
			// 등록 후 등록된 일련번호로 추가 작업이 필요할 때
			Long frontTopicSeq = req.getFrontTopicSeq();

			// 파일 업로드가 있는 경우
			List<FileUploadDto> fileList = req.getFileList();
			commonFileService.insertFileList(true, FileTargetCd.bbs, fileList.toArray(new FileUploadDto[fileList.size()-1]) , frontTopicSeq, Integer.class);
		 */
	}

	/**
	 * 토픽 참여(본인): 로그인 회원을 TOPIC_MEMBER(PARTICIPANT)로 추가합니다.
	 * - 이미 참여 중이면 INSERT IGNORE로 무시됩니다.
	 * - 미가입(= MEMBER_SEQ 없음) 상태에서는 참여 불가하므로, 가입/로그인 후 호출해야 합니다.
	 */
	@Transactional
	public void join(FrontTopicMemberJoinRequest req) {
		MapperUtil.setBaseRequest(req);
		int exists = mapper.countActiveTopic(req.getTopicSeq());
		if (exists <= 0) {
			throw new NotfoundException();
		}
		mapper.insertTopicParticipant(req);
	}

	@Transactional
	public void update(FrontTopicUpdateRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅

		/*
			// 파일 업로드가 있는 경우
			Long frontTopicSeq = req.getFrontTopicSeq();
			List<FileUploadDto> fileList = req.getFileList();
			commonFileService.insertFileList(true, FileTargetCd.bbs, fileList.toArray(new FileUploadDto[fileList.size()-1]) , frontTopicSeq, Integer.class);
		 */

		Long affected = mapper.updateFrontTopic(req);
		if (affected == null || affected.longValue() == 0L) {
			throw new NotfoundException();
		}
	}

	@Transactional
	public void delete(FrontTopicDeleteRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
		Long affected = mapper.deleteFrontTopic(req);
		if (affected == null || affected.longValue() == 0L) {
			throw new NotfoundException();
		}
	}

	@Transactional
	public void revertPick(FrontTopicPickRevertRequest req) {
		MapperUtil.bindMemberSeqFromLogin(req);
		MapperUtil.setBaseRequest(req);
		Long affected = mapper.revertPickByTopicSeq(req);
		if (affected == null || affected.longValue() == 0L) {
			throw new NotfoundException();
		}
	}

}