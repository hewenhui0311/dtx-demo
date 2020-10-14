package com.dtx.seata.tcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dtx.demo.common.ServiceResponse;
import com.dtx.demo.common.contant.ResponseCode;
import com.dtx.seata.tcc.entity.Transfer;
import com.dtx.seata.tcc.service.TransferService;

import io.seata.spring.annotation.GlobalTransactional;

@RestController
public class TransferController {

	@Autowired
	TransferService service;
	
	@GlobalTransactional(timeoutMills = 3000, name = "seata-tcc-demo-trasferTx")
	@PostMapping("/transfer")
	public ServiceResponse<?> transfer(@RequestBody Transfer transfer) {
		ServiceResponse<?> response = ServiceResponse.newInstance();
		if(!service.reserveFrom(null,transfer)) {
			response.setResponseCode(ResponseCode.PRECONDITION_FAILED);
			response.setMessage("余额不足");
			return response;
		}
		service.reserveTo(null, transfer);
		return response;
	}
	
}
