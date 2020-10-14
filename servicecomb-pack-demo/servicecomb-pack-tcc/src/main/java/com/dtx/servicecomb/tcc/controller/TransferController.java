package com.dtx.servicecomb.tcc.controller;

import org.apache.servicecomb.pack.omega.context.annotations.TccStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dtx.demo.common.ServiceResponse;
import com.dtx.demo.common.contant.ResponseCode;
import com.dtx.servicecomb.tcc.entity.Transfer;
import com.dtx.servicecomb.tcc.service.TransferService;

@RestController
public class TransferController {

	@Autowired
	TransferService service;
	
	@TccStart(timeout = 5)
	@PostMapping("/transfer")
	public ServiceResponse<?> transfer(@RequestBody Transfer transfer) {
		ServiceResponse<?> response = ServiceResponse.newInstance();
		if(!service.reserveFrom(transfer)) {
			response.setResponseCode(ResponseCode.PRECONDITION_FAILED);
			response.setMessage("余额不足");
			return response;
		}
		service.reserveTo(transfer);
		return response;
	}
}
