package com.dtx.seata.tcc.service;

import com.dtx.seata.tcc.entity.Transfer;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface TransferService {

	@TwoPhaseBusinessAction(name = "reserveFrom", commitMethod = "confirmFrom", rollbackMethod = "cancelFrom")
	boolean reserveFrom(BusinessActionContext context,
			@BusinessActionContextParameter(paramName = "transfer") Transfer transfer);

	@TwoPhaseBusinessAction(name = "reserveTo", commitMethod = "confirmTo", rollbackMethod = "cancelTo")
	boolean reserveTo(BusinessActionContext context,
			@BusinessActionContextParameter(paramName = "transfer") Transfer transfer);

	boolean confirmFrom(BusinessActionContext context);

	boolean confirmTo(BusinessActionContext context);

	boolean cancelFrom(BusinessActionContext context);

	boolean cancelTo(BusinessActionContext context);
}
