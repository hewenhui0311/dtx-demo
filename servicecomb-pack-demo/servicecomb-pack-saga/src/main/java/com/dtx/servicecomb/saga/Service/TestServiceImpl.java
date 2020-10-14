package com.dtx.servicecomb.saga.Service;

import org.apache.servicecomb.pack.omega.transaction.annotations.Compensable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dtx.servicecomb.saga.entity.Transfer;

@Service
public class TestServiceImpl implements TestService {
	
	@Compensable(compensationMethod = "cancle1")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean test1(Transfer transfer) {
		System.out.println("this is test1");
		return true;
	}

	protected void cancle1(Transfer transfer) {
		System.out.println("this is cancle1");
	}

	@Compensable(compensationMethod = "cancle2")
	@Transactional(timeout = 2,rollbackFor = Exception.class)
	@Override
	public Boolean test2(Transfer transfer) {
		System.out.println("this is test2");
		return true;
	}

	protected void cancle2(Transfer transfer) {
		System.out.println("this is cancle2");
	}

}
