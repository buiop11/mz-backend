package com.matjzing.service;

import com.matjzing.dto.common.*;
import com.matjzing.dto.notice.*;
import com.matjzing.exception.NotfoundException;
import com.matjzing.mapper.AdminNoticeMapper;
import com.matjzing.util.*;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: 김아진
 * @date: 2026-04-23
 * @pname: 관리자
 * @desc: 관리자 서비스 작성
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminNoticeService {

//	private enum AdminNoticeERRCd {
//		ERR_NOTICE_001("공지사항 List 조회 실패"),
//		;
//		private final String desc;
//		public String getDesc() {
//			return desc;
//		}
//		AdminNoticeERRCd(String desc) {
//			this.desc = desc;
//		}
//	}

	private final AdminNoticeMapper mapper;
	private final CommonFileService commonFileService;

	public List<AdminNoticeSelectListResponse> list(AdminNoticeSelectListRequest req) {
		return mapper.selectAdminNoticeList(req);
	}

	public EPageInfo<AdminNoticeSelectPageResponse> page(AdminNoticeSelectPageRequest req) {
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
			EPageInfo<AdminNoticeSelectPageResponse> res = new EPageInfo<>(mapper.selectAdminNoticePage(req));

			for (AdminNoticeSelectPageResponse adminNoticeSelectPageResponse : res.getList()) {
				adminNoticeSelectPageResponse.setFileList(commonFileService.getFileList(true, FileTargetCd.bbs, req.getAdminNoticeSeq()));
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
		return new EPageInfo<>(mapper.selectAdminNoticePage(req));
	}

	public AdminNoticeSelectResponse detail(AdminNoticeSelectRequest req) {

		AdminNoticeSelectResponse response = mapper.selectAdminNotice(req);

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
				response.setFileList(commonFileService.getFileList(true, FileTargetCd.bbs, req.getAdminNoticeSeq()));
			}
		 */
		return response;
	}

	@Transactional
	public void insert(AdminNoticeInsertRequest req) {

		/*
			// 서비스 단 에러처리
			if (dto.getTitle().length() == 1) {
				throw new CustomException(AdminNoticeERRCd.ERR_NOTICE_001.toString(), AdminNoticeERRCd.ERR_NOTICE_001.getDesc());
			}
		 */
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
		mapper.insertAdminNotice(req); // 등록처리

		/*
			// 등록 후 등록된 일련번호로 추가 작업이 필요할 때
			Long adminNoticeSeq = req.getAdminNoticeSeq();

			// 파일 업로드가 있는 경우
			List<FileUploadDto> fileList = req.getFileList();
			commonFileService.insertFileList(true, FileTargetCd.bbs, fileList.toArray(new FileUploadDto[fileList.size()-1]) , adminNoticeSeq, Integer.class);
		 */
	}

	@Transactional
	public void update(AdminNoticeUpdateRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅

		/*
			// 파일 업로드가 있는 경우
			Long adminNoticeSeq = req.getAdminNoticeSeq();
			List<FileUploadDto> fileList = req.getFileList();
			commonFileService.insertFileList(true, FileTargetCd.bbs, fileList.toArray(new FileUploadDto[fileList.size()-1]) , adminNoticeSeq, Integer.class);
		 */

		mapper.updateAdminNotice(req); // 수정처리
	}

	@Transactional
	public void delete(AdminNoticeDeleteRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
		mapper.deleteAdminNotice(req); // 수정처리
	}

}