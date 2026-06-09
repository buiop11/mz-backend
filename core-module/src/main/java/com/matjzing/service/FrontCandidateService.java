package com.matjzing.service;

import com.github.pagehelper.PageHelper;
import com.matjzing.dto.common.enumeration.FileTargetCd;
import com.matjzing.dto.candidate.*;
import com.matjzing.dto.common.EPageInfo;
import com.matjzing.dto.file.FileUploadDto;
import com.matjzing.exception.NotfoundException;
import com.matjzing.mapper.AttachingFileMapper;
import com.matjzing.mapper.FrontCandidateMapper;
import com.matjzing.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author: 김아진
 * @date: 2026-05-11
 * @pname: 관리자
 * @desc: 관리자 서비스 작성
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FrontCandidateService {


	private final FrontCandidateMapper mapper;
	private final CommonFileService commonFileService;
	private final AttachingFileMapper attachingFileMapper;

	public List<FrontCandidateSelectListResponse> list(FrontCandidateSelectListRequest req) {
		return mapper.selectFrontCandidateList(req);
	}

	public EPageInfo<FrontCandidateSelectPageResponse> page(FrontCandidateSelectPageRequest req) {
		PageHelper.startPage(req.getCurrentPage(), 10);
		return new EPageInfo<>(mapper.selectFrontCandidatePage(req));
	}

	public FrontCandidateCountResponse count(FrontCandidateCountRequest req) {
		FrontCandidateCountResponse res = new FrontCandidateCountResponse();
		res.setCount(mapper.selectFrontCandidateCount(req));
		return res;
	}

	public FrontCandidateSelectResponse detail(FrontCandidateSelectRequest req) {

		FrontCandidateSelectResponse response = mapper.selectFrontCandidate(req);

		// 게시판 상세페이지 일 경우 no-data 케이스 용
		if (null == response) {
			throw new NotfoundException();
		}

		// 파일이 있는 경우
		if (null == response) {
			throw new NotfoundException();
		} else {
			response.setFileList(commonFileService.getFileList(true, FileTargetCd.candidate, req.getCandidateSeq()));
		}
		return response;
	}

	@Transactional
	public void insert(FrontCandidateInsertRequest req) {

		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
		// image url은 이미지 따는거라서, file(직접올리는 이미지)이랑 다른거임..!!
		mapper.insertFrontCandidate(req); // 등록처리

		// 등록 후 등록된 일련번호로 추가 작업이 필요할 때
		Long frontCandidateSeq = req.getCandidateSeq();

		// 파일 업로드가 있는 경우
		List<FileUploadDto> fileList = req.getFileList();
		commonFileService.insertFileList(true, FileTargetCd.candidate, fileList.toArray(new FileUploadDto[fileList.size()-1]) , frontCandidateSeq, Integer.class);

	}

	@Transactional
	public void update(FrontCandidateUpdateRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅

		// 파일 업로드가 있는 경우
		Long frontCandidateSeq = req.getCandidateSeq();
		List<FileUploadDto> fileList = req.getFileList();
		commonFileService.insertFileList(true, FileTargetCd.candidate, fileList.toArray(new FileUploadDto[fileList.size()-1]) , frontCandidateSeq, Integer.class);

		mapper.updateFrontCandidate(req); // 수정처리
	}

	@Transactional
	public void delete(FrontCandidateDeleteRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
		mapper.deleteFrontCandidate(req); // 수정처리
	}

	@Transactional
	public void pick(FrontCandidatePickRequest req) {
		MapperUtil.setBaseRequest(req);
		Long affected = mapper.pickTopicByCandidateSeq(req);
		if (affected == null || affected.longValue() == 0L) {
			throw new NotfoundException();
		}
	}

}