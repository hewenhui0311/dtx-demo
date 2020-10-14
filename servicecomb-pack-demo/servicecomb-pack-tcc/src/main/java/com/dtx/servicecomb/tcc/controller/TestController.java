package com.dtx.servicecomb.tcc.controller;

import org.apache.servicecomb.pack.omega.context.annotations.TccStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dtx.demo.common.ServiceResponse;
import com.dtx.demo.common.utils.SequenceGenerator;
import com.dtx.servicecomb.tcc.entity.Transfer;
import com.dtx.servicecomb.tcc.service.TestService;

@RestController
public class TestController {

	@Autowired
	TestService service;
	
	SequenceGenerator sequence = new SequenceGenerator(0);
	
	@TccStart(timeout = 3)
	@PostMapping("test")
	public ServiceResponse<Void> test(@RequestBody Transfer transfer) {
		transfer.setId(sequence.nextId());
		service.reserve1(transfer);
		service.reserve2(transfer);
		return ServiceResponse.newInstance();
	}
	
}
