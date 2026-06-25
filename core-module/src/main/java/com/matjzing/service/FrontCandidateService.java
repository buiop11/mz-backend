package com.matjzing.service;

import com.github.pagehelper.PageHelper;
import com.matjzing.dto.common.enumeration.FileTargetCd;
import com.matjzing.dto.candidate.*;
import com.matjzing.dto.common.EPageInfo;
import com.matjzing.dto.file.FileUploadDto;
import com.matjzing.exception.NotfoundException;
import com.matjzing.mapper.FrontCandidateMapper;
import com.matjzing.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
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

	public List<FrontCandidateSelectListResponse> list(FrontCandidateSelectListRequest req) {
		List<FrontCandidateSelectListResponse> list = mapper.selectFrontCandidateList(req);
		for (FrontCandidateSelectListResponse response : list) {
			response.setFileList(commonFileService.getFileList(true, FileTargetCd.candidate, response.getCandidateSeq()));
		}
		return list;
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

		// 단일 이미지 정책: 파일이 있는 경우 첫 번째 파일 한 건만 등록
		FileUploadDto newFile = extractNewFile(req.getFileList());
		if (null != newFile) {
			commonFileService.insertFile(true, FileTargetCd.candidate, newFile, frontCandidateSeq, Integer.class);
		}
	}

	@Transactional
	public void update(FrontCandidateUpdateRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅

		List<FileUploadDto> fileList = req.getFileList();
		FileUploadDto newFile = extractNewFile(fileList);

		if (null != newFile) {
			// 단일 이미지 정책: 새 파일 등록 후 기존 활성 파일 전체 비활성화(USE_YN=FALSE)
			commonFileService.replaceFile(true, FileTargetCd.candidate, newFile, req.getCandidateSeq());
		} else if (!ObjectUtils.isEmpty(fileList)) {
			// 새 파일 없이 삭제 요청(delYn=true)만 있는 경우: 기존 삭제 로직으로 처리
			commonFileService.insertFileList(true, FileTargetCd.candidate, fileList.toArray(new FileUploadDto[0]), req.getCandidateSeq(), Integer.class);
		}
		// 파일이 아예 안 넘어온 경우는 기존 이미지 유지

		mapper.updateFrontCandidate(req); // 수정처리
	}

	/**
	 * 파일 목록에서 신규 업로드 파일(attachingFileSeq 없음, 삭제 표시 아님)의 첫 번째 건을 추출한다.
	 * 후보는 이미지를 한 장만 사용하므로 그 외 신규 파일은 무시된다.
	 */
	private FileUploadDto extractNewFile(List<FileUploadDto> fileList) {
		if (ObjectUtils.isEmpty(fileList)) {
			return null;
		}
		return fileList.stream()
				.filter(f -> null == f.getAttachingFileSeq() && (null == f.getDelYn() || !f.getDelYn()))
				.findFirst()
				.orElse(null);
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