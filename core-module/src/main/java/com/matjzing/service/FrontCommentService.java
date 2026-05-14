package com.matjzing.service;

import com.matjzing.dto.common.*;
import com.matjzing.dto.comment.*;
import com.matjzing.exception.NotfoundException;
import com.matjzing.mapper.FrontCommentMapper;
import com.matjzing.util.*;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: 김아진
 * @date: 2026-05-14
 * @pname: 관리자
 * @desc: 관리자 서비스 작성
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FrontCommentService {


	private final FrontCommentMapper mapper;
	private final CommonFileService commonFileService;

	public List<FrontCommentSelectListResponse> list(FrontCommentSelectListRequest req) {
		return mapper.selectFrontCommentList(req);
	}

	public EPageInfo<FrontCommentSelectPageResponse> page(FrontCommentSelectPageRequest req) {
		PageHelper.startPage(req.getCurrentPage(), 10);
		return new EPageInfo<>(mapper.selectFrontCommentPage(req));
	}


	@Transactional
	public void insert(FrontCommentInsertRequest req) {

		/*
			// 서비스 단 에러처리
			if (dto.getTitle().length() == 1) {
				throw new CustomException(FrontCommentERRCd.ERR_COMMENT_001.toString(), FrontCommentERRCd.ERR_COMMENT_001.getDesc());
			}
		 */
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
		mapper.insertFrontComment(req); // 등록처리

		/*
			// 등록 후 등록된 일련번호로 추가 작업이 필요할 때
			Long frontCommentSeq = req.getFrontCommentSeq();

			// 파일 업로드가 있는 경우
			List<FileUploadDto> fileList = req.getFileList();
			commonFileService.insertFileList(true, FileTargetCd.bbs, fileList.toArray(new FileUploadDto[fileList.size()-1]) , frontCommentSeq, Integer.class);
		 */
	}

	@Transactional
	public void update(FrontCommentUpdateRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅

		/*
			// 파일 업로드가 있는 경우
			Long frontCommentSeq = req.getFrontCommentSeq();
			List<FileUploadDto> fileList = req.getFileList();
			commonFileService.insertFileList(true, FileTargetCd.bbs, fileList.toArray(new FileUploadDto[fileList.size()-1]) , frontCommentSeq, Integer.class);
		 */

		mapper.updateFrontComment(req); // 수정처리
	}

	@Transactional
	public void delete(FrontCommentDeleteRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
		mapper.deleteFrontComment(req); // 수정처리
	}

}