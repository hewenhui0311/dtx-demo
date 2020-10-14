package com.dtx.servicecomb.saga.controller;

import org.apache.servicecomb.pack.omega.context.annotations.SagaStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dtx.demo.common.ServiceResponse;
import com.dtx.demo.common.contant.ResponseCode;
import com.dtx.demo.common.utils.SequenceGenerator;
import com.dtx.servicecomb.saga.Service.TransferService;
import com.dtx.servicecomb.saga.entity.Transfer;

@RestController
public class TransferController {

	@Autowired
	TransferService service;

	SequenceGenerator sequence = new SequenceGenerator(0);

	/**
	 *    设置超时时间还存在BUG
	 * @param transfer
	 * @return
	 */
	@SagaStart
	@PostMapping("/transfer")
	public ServiceResponse<Void> transfer(@RequestBody Transfer transfer) {
		transfer.setId(sequence.nextId());
		ServiceResponse<Void> response = ServiceResponse.newInstance();
		if (!service.transferFrom(transfer)) {
			response.setResponseCode(ResponseCode.PRECONDITION_FAILED);
			response.setMessage("余额不足");
			return response;
		}
		if (!service.transferTo(transfer)) {
			service.compentsateFrom(transfer);
			response.setResponseCode(ResponseCode.PRECONDITION_FAILED);
			response.setMessage("账户[" + transfer.getToAccountNo() + "]不存在");
			return response;
		}
		service.doTxSuccessState(transfer.getId());
		response.setMessage("转账成功");
		return response;
	}

}
