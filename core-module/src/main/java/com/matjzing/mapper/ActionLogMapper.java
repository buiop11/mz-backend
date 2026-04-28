package com.matjzing.mapper;

import com.matjzing.dto.actionlog.ActionLogInsertRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionLogMapper {

	Long insertMemberActionLog(ActionLogInsertRequest req);
	Long insertManagerActionLog(ActionLogInsertRequest req);

}