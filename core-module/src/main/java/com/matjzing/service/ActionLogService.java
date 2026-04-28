package com.matjzing.service;

import com.matjzing.dto.actionlog.ActionLogInsertRequest;
import com.matjzing.mapper.ActionLogMapper;
import com.matjzing.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActionLogService {
	private final ActionLogMapper mapper;

	@Transactional
	public void insertMemberActionLog(ActionLogInsertRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
		mapper.insertMemberActionLog(req); // 등록처리
	}

	@Transactional
	public void insertManagerActionLog(ActionLogInsertRequest req) {
		MapperUtil.setBaseRequest(req); // BaseRequest 셋팅
		mapper.insertManagerActionLog(req); // 등록처리
	}

}