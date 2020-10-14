package com.dtx.seata.at.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dtx.demo.common.ServiceResponse;
import com.dtx.demo.common.contant.ResponseCode;
import com.dtx.demo.common.utils.SequenceGenerator;
import com.dtx.seata.at.entity.Transfer;
import com.dtx.seata.at.service.TransferService;

import io.seata.spring.annotation.GlobalTransactional;

@RestController
public class TransferController {

	@Autowired
	TransferService service;
	
	SequenceGenerator sequence = new SequenceGenerator(0);
	
	@GlobalTransactional(timeoutMills = 30000, name = "seata-at-demo-transferTx")
	@PostMapping("/transfer")
	public ServiceResponse<?> transfer(@RequestBody Transfer transfer) {
		transfer.setId(sequence.nextId());
		ServiceResponse<?> response = ServiceResponse.newInstance();
		if(!service.transferFrom(transfer)) {
			response.setResponseCode(ResponseCode.PRECONDITION_FAILED);
			response.setMessage("余额不足");
			return response;
		}
		service.transferTo(transfer);	
		service.doTxSuccessState(transfer.getId());
//		int i=1/0;
		return response;
	}
}
