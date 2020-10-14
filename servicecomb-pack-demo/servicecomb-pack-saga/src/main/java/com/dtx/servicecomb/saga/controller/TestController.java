package com.dtx.servicecomb.saga.controller;

import org.apache.servicecomb.pack.omega.context.annotations.SagaStart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dtx.demo.common.ServiceResponse;
import com.dtx.demo.common.utils.SequenceGenerator;
import com.dtx.servicecomb.saga.Service.TestService;
import com.dtx.servicecomb.saga.entity.Transfer;

@RestController
public class TestController {

	@Autowired
	TestService service;
	
	SequenceGenerator sequence = new SequenceGenerator(0);
	
	/**
	 *     超时设置还有BUG
	 * @param transfer
	 * @return
	 */
	@SagaStart
	@PostMapping("test")
	public ServiceResponse<Void> test(@RequestBody Transfer transfer) {
		transfer.setId(sequence.nextId());
		service.test1(transfer);
		service.test2(transfer);
		return ServiceResponse.newInstance();
	}
	
	/**
	 *     超时还存在BUG
	 * @param transfer
	 * @return
	 */
	@SagaStart(timeout = 5)
	@PostMapping("test1")
	public ServiceResponse<Void> test2(@RequestBody Transfer transfer) {
		transfer.setId(sequence.nextId());
		service.test1(transfer);
		service.test2(transfer);
		return ServiceResponse.newInstance();
	}
}
